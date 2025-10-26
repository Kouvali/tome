package org.kouv.tome.api.skill.manager;

import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillResponse;

public interface SkillManager {
    SkillResponse testSkill(RegistryEntry<? extends Skill> skill);

    SkillResponse castSkill(RegistryEntry<? extends Skill> skill);
}
