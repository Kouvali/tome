package org.kouv.tome.impl.skill;

import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

public final class SkillContextImpl<S> implements SkillContext<S> {
    private final Holder<? extends Skill<S>> skill;
    private final LivingEntity source;

    public SkillContextImpl(Holder<? extends Skill<S>> skill, LivingEntity source) {
        this.skill = Objects.requireNonNull(skill);
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public Holder<? extends Skill<S>> getSkill() {
        return skill;
    }

    @Override
    public LivingEntity getSource() {
        return source;
    }

    @Override
    public String toString() {
        return "SkillContextImpl{" +
                "skill=" + skill +
                ", source=" + source +
                '}';
    }
}
