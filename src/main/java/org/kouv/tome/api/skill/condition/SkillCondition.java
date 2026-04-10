package org.kouv.tome.api.skill.condition;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;

import java.util.Objects;

@FunctionalInterface
public interface SkillCondition {
    static SkillCondition requireCreativeMode() {
        return context -> context.getSource() instanceof Player player && player.isCreative() ?
                SkillResponse.success() :
                SkillResponse.unavailable();
    }

    static SkillCondition requireLearned() {
        return context -> context.getSource().getSkillContainer().hasSkill(context.getSkill()) ?
                SkillResponse.success() :
                SkillResponse.notLearned();
    }

    static SkillCondition requireNoCooldown() {
        return requireCreativeMode().or(context ->
                context.getSource().getSkillCooldownManager().isCoolingDown(context.getSkill()) ?
                        SkillResponse.cooldown() :
                        SkillResponse.success()
        );
    }

    static SkillCondition requireInGame() {
        return context -> context.getSource().canBeSeenByAnyone() ?
                SkillResponse.success() :
                SkillResponse.unavailable();
    }

    static SkillCondition defaultConditions() {
        return requireLearned()
                .and(requireNoCooldown())
                .and(requireInGame());
    }

    SkillResponse test(SkillContext<?> context);

    @ApiStatus.NonExtendable
    default SkillCondition and(SkillCondition other) {
        Objects.requireNonNull(other);
        return context -> test(context) instanceof SkillResponse.Failure failure ?
                failure :
                other.test(context);
    }

    @ApiStatus.NonExtendable
    default SkillCondition or(SkillCondition other) {
        Objects.requireNonNull(other);
        return context -> test(context) instanceof SkillResponse.Success success ?
                success :
                other.test(context);
    }
}
