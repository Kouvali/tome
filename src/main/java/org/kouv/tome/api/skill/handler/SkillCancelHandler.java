package org.kouv.tome.api.skill.handler;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

@FunctionalInterface
public interface SkillCancelHandler<S> {
    static <S> SkillCancelHandler<S> alwaysAllow() {
        return instance -> true;
    }

    static <S> SkillCancelHandler<S> alwaysDeny() {
        return instance -> false;
    }

    boolean handle(SkillInstance<? extends S> instance);

    @ApiStatus.NonExtendable
    default SkillCancelHandler<S> and(SkillCancelHandler<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> handle(instance) && other.handle(instance);
    }

    @ApiStatus.NonExtendable
    default SkillCancelHandler<S> or(SkillCancelHandler<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> handle(instance) || other.handle(instance);
    }
}
