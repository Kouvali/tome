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
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillManagerImpl implements SkillManager {
    private final WeakReference<? extends LivingEntity> sourceRef;

    private final Map<RegistryEntry<? extends Skill<?>>, SkillInstance<?>> instances = new ConcurrentHashMap<>();

    public SkillManagerImpl(LivingEntity source) {
        this.sourceRef = new WeakReference<>(Objects.requireNonNull(source));
    }

    @Override
    public boolean isCasting(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return instances.get(skill) != null;
    }

    @Override
    public boolean isCastingAny() {
        return !instances.isEmpty();
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <S> SkillInstance<S> getCastingInstance(RegistryEntry<? extends Skill<S>> skill) {
        Objects.requireNonNull(skill);
        return (SkillInstance<S>) instances.get(skill);
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
    public boolean completeCasting(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        if (!isCasting(skill)) {
            return false;
        }

        executeComplete(instances.get(skill));
        return true;
    }

    @Override
    public boolean completeCastingAll() {
        if (!isCastingAny()) {
            return false;
        }

        instances.values().forEach(this::executeComplete);
        return true;
    }

    @Override
    public boolean cancelCasting(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return isCasting(skill) && executeCancel(instances.get(skill));
    }

    @Override
    public int cancelCastingAll() {
        return (int) instances.values()
                .stream()
                .filter(this::executeCancel)
                .count();
    }

    @Override
    public boolean interruptCasting(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return isCasting(skill) && executeInterrupt(instances.get(skill));
    }

    @Override
    public int interruptCastingAll() {
        return (int) instances.values()
                .stream()
                .filter(this::executeInterrupt)
                .count();
    }

    @Override
    public boolean terminateCasting(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        if (!isCasting(skill)) {
            return false;
        }

        executeEnd(instances.get(skill));
        return true;
    }

    @Override
    public boolean terminateCastingAll() {
        if (!isCastingAny()) {
            return false;
        }

        instances.values().forEach(this::executeEnd);
        return true;
    }

    public void update() {
        instances.values().forEach(this::executeTick);
    }

    private <S> SkillResponse executeTest(RegistryEntry<? extends Skill<S>> skill) {
        return isCasting(skill) ?
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
        instances.put(instance.getSkill(), instance);
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
        instances.remove(instance.getSkill());
    }

    private <S> void executeTick(SkillInstance<S> instance) {
        instance.getSkill().value().getTickBehavior().execute(instance);
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
