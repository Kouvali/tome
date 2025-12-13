package org.kouv.tome.api.skill.callback;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;

@FunctionalInterface
public interface SkillEntityLoadedCallback {
    static SkillEntityLoadedCallback noOp() {
        return context -> {};
    }

    void handle(SkillContext<?> context);

    @ApiStatus.NonExtendable
    default SkillEntityLoadedCallback andThen(SkillEntityLoadedCallback after) {
        Objects.requireNonNull(after);
        return context -> {
            handle(context);
            after.handle(context);
        };
    }
}
