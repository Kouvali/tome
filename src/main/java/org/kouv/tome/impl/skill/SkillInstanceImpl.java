package org.kouv.tome.impl.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

public final class SkillInstanceImpl<S> implements SkillInstance<S> {
    private final SkillContext<S> context;
    private final S state;
    private final int startTime;

    public SkillInstanceImpl(SkillContext<S> context, S state) {
        this.context = Objects.requireNonNull(context);
        this.state = Objects.requireNonNull(state);
        this.startTime = getCurrentTime();
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public int getCurrentTime() {
        return ((ServerWorld) getSource().getEntityWorld()).getServer().getTicks();
    }

    @Override
    public int getStartTime() {
        return startTime;
    }

    @Override
    public int getElapsedTime() {
        return getCurrentTime() - getStartTime();
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
