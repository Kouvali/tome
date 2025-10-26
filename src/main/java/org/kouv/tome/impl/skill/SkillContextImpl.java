package org.kouv.tome.impl.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;

public final class SkillContextImpl implements SkillContext {
    private final RegistryEntry<? extends Skill> skill;
    private final LivingEntity source;

    public SkillContextImpl(
            RegistryEntry<? extends Skill> skill,
            LivingEntity source
    ) {
        this.skill = Objects.requireNonNull(skill);
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public RegistryEntry<? extends Skill> getSkill() {
        return skill;
    }

    @Override
    public LivingEntity getSource() {
        return source;
    }
}
