package org.kouv.tome.api.skill.behavior;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

@FunctionalInterface
public interface SkillStartBehavior<S> {
    static <S> SkillStartBehavior<S> noOp() {
        return instance -> {};
    }

    void execute(SkillInstance<? extends S> instance);

    @ApiStatus.NonExtendable
    default SkillStartBehavior<S> andThen(SkillStartBehavior<? super S> after) {
        Objects.requireNonNull(after);
        return instance -> {
            execute(instance);
            after.execute(instance);
        };
    }
}
