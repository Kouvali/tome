package org.kouv.tome.api.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;

public interface SkillContext<S> {
    RegistryEntry<? extends Skill<S>> getSkill();

    LivingEntity getSource();
}
