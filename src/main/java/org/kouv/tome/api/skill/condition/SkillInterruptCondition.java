package org.kouv.tome.api.skill.condition;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

@FunctionalInterface
public interface SkillInterruptCondition<S> {
    static <S> SkillInterruptCondition<S> allowed() {
        return instance -> true;
    }

    static <S> SkillInterruptCondition<S> denied() {
        return instance -> false;
    }

    boolean test(SkillInstance<? extends S> instance);

    @ApiStatus.NonExtendable
    default SkillInterruptCondition<S> and(SkillInterruptCondition<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> test(instance) && other.test(instance);
    }

    @ApiStatus.NonExtendable
    default SkillInterruptCondition<S> or(SkillInterruptCondition<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> test(instance) || other.test(instance);
    }
}
