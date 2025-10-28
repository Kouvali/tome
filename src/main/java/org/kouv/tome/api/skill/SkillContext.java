package org.kouv.tome.api.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.manager.SkillManager;

public interface SkillContext<S> {
    RegistryEntry<? extends Skill<S>> getSkill();

    LivingEntity getSource();

    default SkillContainer getSkillContainer() {
        return getSource().getSkillContainer();
    }

    default SkillCooldownManager getSkillCooldownManager() {
        return getSource().getSkillCooldownManager();
    }

    default SkillManager getSkillManager() {
        return getSource().getSkillManager();
    }
}
