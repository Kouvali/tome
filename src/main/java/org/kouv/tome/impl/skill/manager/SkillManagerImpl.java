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
    private @Nullable SkillInstance<?> instance;

    public SkillManagerImpl(
            LivingEntity sourceRef
    ) {
        this.sourceRef = new WeakReference<>(Objects.requireNonNull(sourceRef));
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
    public @Nullable <S> SkillInstance<S> getCastingInstance(Class<? extends S> clazz) {
        Objects.requireNonNull(clazz);
        return instance == null || !clazz.isInstance(instance.getState()) ?
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
    public boolean cancelCasting() {
        return isCasting() && stopCasting(instance, true);
    }

    @Override
    public boolean interruptCasting() {
        return isCasting() && stopCasting(instance, false);
    }

    @Override
    public boolean completeCasting() {
        if (!isCasting()) {
            return false;
        }

        completeCasting(instance);
        return true;
    }

    @Override
    public boolean terminateCasting() {
        if (!isCasting()) {
            return false;
        }

        endCasting(instance);
        return true;
    }

    public void update() {
        if (isCasting()) {
            updateCasting(instance);
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
                SkillInstance<S> instance = createInstance(
                        context,
                        ok.state(),
                        skill.value().getDurationProvider().get(context)
                );
                beginCasting(instance);
                if (instance.getDuration() <= 0) {
                    completeCasting(instance);
                }

                yield SkillResponse.success();
            }
        };
    }

    private <S> void beginCasting(SkillInstance<S> instance) {
        this.instance = instance;
        instance.getSkill().value().getStartBehavior().execute(instance);
    }

    private <S> void endCasting(SkillInstance<S> instance) {
        instance.getSkill().value().getEndBehavior().execute(instance);
        this.instance = null;
    }

    private <S> boolean stopCasting(SkillInstance<S> instance, boolean cancel) {
        boolean isAccepted = cancel ?
                instance.getSkill().value().getCancelHandler().handle(instance) :
                instance.getSkill().value().getInterruptHandler().handle(instance);

        if (isAccepted) {
            endCasting(instance);
        }

        return isAccepted;
    }

    private <S> void completeCasting(SkillInstance<S> instance) {
        instance.getSkill().value().getCompleteBehavior().execute(instance);
        endCasting(instance);
    }

    private <S> void updateCasting(SkillInstance<S> instance) {
        if (instance.getDuration() > 0) {
            instance.setDuration(instance.getDuration() - 1);
            instance.getSkill().value().getTickBehavior().execute(instance);
            if (instance.getDuration() == 0) {
                completeCasting(instance);
            }
        }
    }

    private <S> SkillContext<S> createContext(RegistryEntry<? extends Skill<S>> skill) {
        return new SkillContextImpl<>(skill, getSource());
    }

    private <S> SkillInstance<S> createInstance(SkillContext<S> context, S state, int duration) {
        return new SkillInstanceImpl<>(context, state, duration);
    }

    private LivingEntity getSource() {
        return Optional.ofNullable(sourceRef.get())
                .orElseThrow(() -> new IllegalStateException("Entity is no longer available"));
    }
}
