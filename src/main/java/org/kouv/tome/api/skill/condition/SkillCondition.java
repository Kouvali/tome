package org.kouv.tome.api.skill.condition;

import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface SkillCondition {
    static SkillCondition requireLearned(
            Function<? super SkillContext<?>, ? extends RegistryEntry<? extends Skill<?>>> skillProvider
    ) {
        Objects.requireNonNull(skillProvider);
        return context -> context.getSource().getSkillContainer().hasSkill(skillProvider.apply(context)) ?
                SkillResponse.success() :
                SkillResponse.notLearned();
    }

    static SkillCondition requireLearned() {
        return requireLearned(SkillContext::getSkill);
    }

    static SkillCondition requireNoCooldown(
            Function<? super SkillContext<?>, ? extends RegistryEntry<? extends Skill<?>>> skillProvider
    ) {
        Objects.requireNonNull(skillProvider);
        return context -> context.getSource().getSkillCooldownManager().isCoolingDown(skillProvider.apply(context)) ?
                SkillResponse.cooldown() :
                SkillResponse.success();
    }

    static SkillCondition requireNoCooldown() {
        return requireNoCooldown(SkillContext::getSkill);
    }

    static SkillCondition requireInGame() {
        return context -> context.getSource().isPartOfGame() ?
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
