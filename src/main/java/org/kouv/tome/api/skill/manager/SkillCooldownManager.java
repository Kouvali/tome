package org.kouv.tome.api.skill.manager;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.kouv.tome.api.skill.Skill;

public interface SkillCooldownManager {
    boolean isCoolingDown(RegistryEntry<? extends Skill<?>> skill);

    boolean isCoolingDown(Identifier id);

    int getCooldown(RegistryEntry<? extends Skill<?>> skill);

    int getCooldown(Identifier id);

    void setCooldown(RegistryEntry<? extends Skill<?>> skill, int cooldown);

    void setCooldown(Identifier id, int cooldown);
}
