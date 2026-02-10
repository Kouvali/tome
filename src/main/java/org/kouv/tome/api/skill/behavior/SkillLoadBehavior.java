package org.kouv.tome.api.skill.behavior;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;

@FunctionalInterface
public interface SkillLoadBehavior {
    static SkillLoadBehavior noOp() {
        return context -> {};
    }

    void execute(SkillContext<?> context);

    @ApiStatus.NonExtendable
    default SkillLoadBehavior andThen(SkillLoadBehavior after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
