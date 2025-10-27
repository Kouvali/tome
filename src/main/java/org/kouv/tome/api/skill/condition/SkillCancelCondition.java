package org.kouv.tome.api.skill.condition;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

@FunctionalInterface
public interface SkillCancelCondition<S> {
    static <S> SkillCancelCondition<S> allowed() {
        return instance -> true;
    }

    static <S> SkillCancelCondition<S> denied() {
        return instance -> false;
    }

    boolean test(SkillInstance<? extends S> instance);

    @ApiStatus.NonExtendable
    default SkillCancelCondition<S> and(SkillCancelCondition<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> test(instance) && other.test(instance);
    }

    @ApiStatus.NonExtendable
    default SkillCancelCondition<S> or(SkillCancelCondition<? super S> other) {
        Objects.requireNonNull(other);
        return instance -> test(instance) || other.test(instance);
    }
}
