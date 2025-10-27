package org.kouv.tome.impl.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

public final class SkillInstanceImpl<S> implements SkillInstance<S> {
    private final SkillContext<S> context;
    private final S state;

    public SkillInstanceImpl(SkillContext<S> context, S state) {
        this.context = Objects.requireNonNull(context);
        this.state = Objects.requireNonNull(state);
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public RegistryEntry<? extends Skill<S>> getSkill() {
        return context.getSkill();
    }

    @Override
    public LivingEntity getSource() {
        return context.getSource();
    }
}
