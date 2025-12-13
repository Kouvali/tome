package org.kouv.tome.api.skill.manager;

import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;

public interface SkillCooldownManager {
    boolean isCoolingDown(RegistryEntry<? extends Skill<?>> skill);

    int getCooldown(RegistryEntry<? extends Skill<?>> skill);

    void setCooldown(RegistryEntry<? extends Skill<?>> skill, int cooldown);
}
