package org.kouv.tome.api.skill.callback;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;

@FunctionalInterface
public interface SkillRemovedCallback {
    static SkillRemovedCallback noOp() {
        return context -> {};
    }

    void handle(SkillContext<?> context);

    @ApiStatus.NonExtendable
    default SkillRemovedCallback andThen(SkillRemovedCallback after) {
        Objects.requireNonNull(after);
        return context -> {
            handle(context);
            after.handle(context);
        };
    }
}
