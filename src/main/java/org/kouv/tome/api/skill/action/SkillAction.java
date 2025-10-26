package org.kouv.tome.api.skill.action;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;

import java.util.Objects;

@FunctionalInterface
public interface SkillAction {
    static SkillAction alwaysSuccess() {
        return context -> SkillResponse.success();
    }

    static SkillAction alwaysUnavailable() {
        return context -> SkillResponse.unavailable();
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
