package org.kouv.tome.api.skill;

import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;

import java.util.Objects;

public sealed interface SkillResponse {
    static Success success() {
        return new Success();
    }

    static Failure failure(Text reason) {
        return new Failure(reason);
    }

    static Failure inProgress() {
        return failure(Text.translatable("skill.failure.in_progress"));
    }

    static Failure notLearned() {
        return failure(Text.translatable("skill.failure.not_learned"));
    }

    static Failure cooldown() {
        return failure(Text.translatable("skill.failure.cooldown"));
    }

    static Failure unavailable() {
        return failure(Text.translatable("skill.failure.unavailable"));
    }

    record Success() implements SkillResponse {
        @ApiStatus.Internal
        public Success {
        }
    }

    record Failure(Text reason) implements SkillResponse {
        @ApiStatus.Internal
        public Failure {
            Objects.requireNonNull(reason);
        }
    }
}
