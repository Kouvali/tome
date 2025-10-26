package org.kouv.tome.api.skill.action;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;

import java.util.Objects;
import java.util.function.Consumer;

@FunctionalInterface
public interface SkillAction {
    static SkillAction alwaysSuccess(Consumer<? super SkillContext> action) {
        return context -> {
            action.accept(context);
            return SkillResponse.success();
        };
    }

    static SkillAction alwaysSuccess() {
        return alwaysSuccess(context -> {});
    }

    static SkillAction alwaysFailure(SkillResponse.Failure failure, Consumer<? super SkillContext> action) {
        return context -> {
            action.accept(context);
            return failure;
        };
    }

    static SkillAction alwaysFailure(SkillResponse.Failure failure) {
        return alwaysFailure(failure, context -> {});
    }

    SkillResponse execute(SkillContext context);

    @ApiStatus.NonExtendable
    default SkillAction and(SkillAction other) {
        Objects.requireNonNull(other);
        return context -> execute(context) instanceof SkillResponse.Failure failure ?
                failure :
                other.execute(context);
    }

    @ApiStatus.NonExtendable
    default SkillAction or(SkillAction other) {
        Objects.requireNonNull(other);
        return context -> execute(context) instanceof SkillResponse.Success success ?
                success :
                other.execute(context);
    }
}
