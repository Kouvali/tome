package org.kouv.tome.api.skill.behavior;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

@FunctionalInterface
public interface SkillEndBehavior<S> {
    static <S> SkillEndBehavior<S> noOp() {
        return instance -> {};
    }

    void execute(SkillInstance<? extends S> instance);

    @ApiStatus.NonExtendable
    default SkillEndBehavior<S> andThen(SkillEndBehavior<? super S> after) {
        Objects.requireNonNull(after);
        return instance -> {
            execute(instance);
            after.execute(instance);
        };
    }
}
