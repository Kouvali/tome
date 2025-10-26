package org.kouv.tome.api.skill.manager;

import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;

import java.util.Set;

public interface SkillContainer {
    Set<? extends RegistryEntry<? extends Skill>> getSkills();

    boolean hasSkill(RegistryEntry<? extends Skill> skill);

    boolean addSkill(RegistryEntry<? extends Skill> skill);

    boolean removeSkill(RegistryEntry<? extends Skill> skill);
}
