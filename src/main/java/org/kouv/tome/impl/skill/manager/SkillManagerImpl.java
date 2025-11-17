package org.kouv.tome.impl.skill.manager;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillInstance;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.kouv.tome.api.skill.state.SkillStateCreationResult;
import org.kouv.tome.impl.skill.SkillContextImpl;
import org.kouv.tome.impl.skill.SkillInstanceImpl;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;

public final class SkillManagerImpl implements SkillManager {
    private final WeakReference<? extends LivingEntity> sourceRef;

    private @Nullable SkillInstance<?> instance = null;

    public SkillManagerImpl(LivingEntity source) {
        this.sourceRef = new WeakReference<>(Objects.requireNonNull(source));
    }

    @Override
    public boolean isCasting() {
        return instance != null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <S> SkillInstance<S> getCastingInstance(RegistryEntry<? extends Skill<S>> skill) {
        Objects.requireNonNull(skill);
        return instance == null || !instance.getSkill().equals(skill) ?
                null :
                (SkillInstance<S>) instance;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SkillResponse testSkill(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return executeTest(
                (RegistryEntry<? extends Skill<Object>>) skill
        );
    }

    @SuppressWarnings("unchecked")
    @Override
    public SkillResponse castSkill(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return executeCast(
                (RegistryEntry<? extends Skill<Object>>) skill
        );
    }

    @Override
    public boolean completeCasting() {
        if (!isCasting()) {
            return false;
        }

        executeComplete(instance);
        return true;
    }

    @Override
    public boolean cancelCasting() {
        return isCasting() && executeCancel(instance);
    }

    @Override
    public boolean interruptCasting() {
        return isCasting() && executeInterrupt(instance);
    }

    @Override
    public boolean terminateCasting() {
        if (!isCasting()) {
            return false;
        }

        executeEnd(instance);
        return true;
    }

    public void update() {
        if (isCasting()) {
            executeTick(instance);
        }
    }

    private <S> SkillResponse executeTest(RegistryEntry<? extends Skill<S>> skill) {
        return isCasting() ?
                SkillResponse.inProgress() :
                skill.value().getCondition().test(createContext(skill));
    }

    private <S> SkillResponse executeCast(RegistryEntry<? extends Skill<S>> skill) {
        if (testSkill(skill) instanceof SkillResponse.Failure failure) {
            return failure;
        }

        SkillContext<S> context = createContext(skill);
        return switch (skill.value().getStateFactory().create(context)) {
            case SkillStateCreationResult.Error<S> error -> error.failure();
            case SkillStateCreationResult.Ok<S> ok -> {
                executeStart(createInstance(context, ok.state()));
                yield SkillResponse.success();
            }
        };
    }

    private <S> void executeStart(SkillInstance<S> instance) {
        this.instance = instance;
        instance.getSkill().value().getStartBehavior().execute(instance);
    }

    private <S> void executeComplete(SkillInstance<S> instance) {
        instance.getSkill().value().getCompleteBehavior().execute(instance);
        executeEnd(instance);
    }

    private <S> boolean executeCancel(SkillInstance<S> instance) {
        if (!instance.getSkill().value().getCancelCondition().test(instance)) {
            return false;
        }

        instance.getSkill().value().getCancelBehavior().execute(instance);
        executeEnd(instance);
        return true;
    }

    private <S> boolean executeInterrupt(SkillInstance<S> instance) {
        if (!instance.getSkill().value().getInterruptCondition().test(instance)) {
            return false;
        }

        instance.getSkill().value().getInterruptBehavior().execute(instance);
        executeEnd(instance);
        return true;
    }

    private <S> void executeEnd(SkillInstance<S> instance) {
        instance.getSkill().value().getEndBehavior().execute(instance);
        this.instance = null;
    }

    private <S> void executeTick(SkillInstance<S> instance) {
        if (instance.getSkill().value().getDuration() > 0 &&
                instance.getSkill().value().getDuration() < instance.getElapsedTime()
        ) {
            instance.getSkill().value().getTickBehavior().execute(instance);
        } else {
            completeCasting();
        }
    }

    private <S> SkillContext<S> createContext(RegistryEntry<? extends Skill<S>> skill) {
        return new SkillContextImpl<>(skill, getSource());
    }

    private <S> SkillInstance<S> createInstance(SkillContext<S> context, S state) {
        return new SkillInstanceImpl<>(context, state);
    }

    private LivingEntity getSource() {
        return Optional.ofNullable(sourceRef.get())
                .orElseThrow(() -> new IllegalStateException("Entity is no longer available"));
    }
}
