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
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class SkillManagerImpl implements SkillManager {
    private final WeakReference<? extends LivingEntity> sourceRef;

    private @Nullable SkillInstance<?> instance = null;
    private Status status = Status.IDLE;

    public SkillManagerImpl(
            LivingEntity sourceRef
    ) {
        this.sourceRef = new WeakReference<>(Objects.requireNonNull(sourceRef));
    }

    @Override
    public boolean isCasting() {
        return status != Status.IDLE;
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
        return status != Status.IDLE ?
                SkillResponse.inProgress() :
                skill.value().getCondition().test(createContext((RegistryEntry<? extends Skill<Object>>) skill));
    }

    @SuppressWarnings("unchecked")
    @Override
    public SkillResponse castSkill(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        if (status != Status.IDLE) {
            return SkillResponse.inProgress();
        }

        RegistryEntry<? extends Skill<Object>> objectSkill = (RegistryEntry<? extends Skill<Object>>) skill;
        SkillResponse testResponse = testSkill(objectSkill);
        if (testResponse instanceof SkillResponse.Failure) {
            return testResponse;
        }

        SkillContext<Object> context = createContext(objectSkill);
        SkillStateCreationResult<Object> creationResult = objectSkill.value().getStateFactory().create(context);
        return switch (creationResult) {
            case SkillStateCreationResult.Error<Object> error -> error.failure();
            case SkillStateCreationResult.Ok<Object> ok -> {
                status = Status.STARTING;
                instance = createInstance(context, ok.state(), objectSkill.value().getDurationProvider().get(context));
                try {
                    executeBehavior(instance, it -> it.getSkill().value().getStartBehavior().execute(it));
                    yield SkillResponse.success();
                } finally {
                    status = Status.ACTIVE;
                }
            }
        };
    }

    @Override
    public boolean cancelCasting() {
        if (status != Status.ACTIVE) {
            return false;
        }

        assert instance != null;
        if (!testCondition(instance, it -> it.getSkill().value().getCancelHandler().handle(it))) {
            return false;
        }

        status = Status.CANCELLING;
        try {
            executeBehavior(instance, it -> it.getSkill().value().getCancelBehavior().execute(it));
            return true;
        } finally {
            terminateCasting();
        }
    }

    @Override
    public boolean interruptCasting() {
        if (status != Status.ACTIVE) {
            return false;
        }

        assert instance != null;
        if (!testCondition(instance, it -> it.getSkill().value().getInterruptHandler().handle(it))) {
            return false;
        }

        status = Status.INTERRUPTING;
        try {
            executeBehavior(instance, it -> it.getSkill().value().getInterruptBehavior().execute(it));
            return true;
        } finally {
            terminateCasting();
        }
    }

    @Override
    public boolean completeCasting() {
        if (status != Status.ACTIVE) {
            return false;
        }

        assert instance != null;
        status = Status.COMPLETING;
        try {
            executeBehavior(instance, it -> it.getSkill().value().getCompleteBehavior().execute(it));
            return true;
        } finally {
            terminateCasting();
        }
    }

    @Override
    public boolean terminateCasting() {
        if (status == Status.IDLE || status == Status.ENDING) {
            return false;
        }

        assert instance != null;
        status = Status.ENDING;
        try {
            executeBehavior(instance, it -> it.getSkill().value().getEndBehavior().execute(it));
            return true;
        } finally {
            instance = null;
            status = Status.IDLE;
        }
    }

    public void update() {
        if (status != Status.ACTIVE) {
            return;
        }

        assert instance != null;
        if (instance.getDuration() == 0) {
            completeCasting();
            return;
        }

        if (instance.getDuration() < 0) {
            executeBehavior(instance, it -> it.getSkill().value().getTickBehavior().execute(it));
            return;
        }

        instance.setDuration(instance.getDuration() - 1);
        executeBehavior(instance, it -> it.getSkill().value().getTickBehavior().execute(it));
        if (instance.getDuration() == 0) {
            completeCasting();
        }
    }

    private <S> void executeBehavior(SkillInstance<S> instance, Consumer<? super SkillInstance<S>> consumer) {
        consumer.accept(instance);
    }

    private <S> boolean testCondition(SkillInstance<S> instance, Predicate<? super SkillInstance<S>> predicate) {
        return predicate.test(instance);
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

    private enum Status {
        IDLE,
        STARTING,
        ACTIVE,
        COMPLETING,
        CANCELLING,
        INTERRUPTING,
        ENDING
    }
}
