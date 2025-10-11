package org.kouv.tome.api.skill.handler;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

@FunctionalInterface
public interface SkillInterruptHandler<S> {
    static <S> SkillInterruptHandler<S> alwaysAllow() {
        return instance -> true;
    }

    static <S> SkillInterruptHandler<S> alwaysDeny() {
        return instance -> false;
    }

    boolean handle(SkillInstance<? extends S> instance);

    @ApiStatus.NonExtendable
    default SkillInterruptHandler<S> and(SkillInterruptHandler<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> handle(instance) && other.handle(instance);
    }

    @ApiStatus.NonExtendable
    default SkillInterruptHandler<S> or(SkillInterruptHandler<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> handle(instance) || other.handle(instance);
    }
}
