package org.kouv.tome.impl.skill;

import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

public final class SkillInstanceImpl<S> implements SkillInstance<S> {
    private final SkillContext<S> context;
    private final S state;

    private final AttributeModifierTracker attributeModifierTracker;
    private int duration;

    private boolean shouldComplete;
    private int elapsedTime;

    public SkillInstanceImpl(SkillContext<S> context, S state, AttributeModifierTracker attributeModifierTracker, int duration) {
        this.context = Objects.requireNonNull(context);
        this.state = Objects.requireNonNull(state);
        this.attributeModifierTracker = Objects.requireNonNull(attributeModifierTracker);
        this.duration = duration;
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public AttributeModifierTracker getAttributeModifierTracker() {
        return attributeModifierTracker;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean isShouldComplete() {
        return shouldComplete;
    }

    @Override
    public void setShouldComplete(boolean shouldComplete) {
        this.shouldComplete = shouldComplete;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public Holder<? extends Skill<S>> getSkill() {
        return context.getSkill();
    }

    @Override
    public LivingEntity getSource() {
        return context.getSource();
    }

    public void update() {
        elapsedTime++;
    }

    @Override
    public String toString() {
        return "SkillInstanceImpl{" +
                "context=" + context +
                ", state=" + state +
                ", shouldComplete=" + shouldComplete +
                ", duration=" + duration +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
