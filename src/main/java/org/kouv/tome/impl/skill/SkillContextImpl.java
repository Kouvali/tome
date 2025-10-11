package org.kouv.tome.impl.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;

public final class SkillContextImpl<S> implements SkillContext<S> {
    private final RegistryEntry<? extends Skill<S>> skill;
    private final LivingEntity source;

    public SkillContextImpl(
            RegistryEntry<? extends Skill<S>> skill,
            LivingEntity source
    ) {
        this.skill = Objects.requireNonNull(skill);
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public RegistryEntry<? extends Skill<S>> getSkill() {
        return skill;
    }

    @Override
    public LivingEntity getSource() {
        return source;
    }
}
