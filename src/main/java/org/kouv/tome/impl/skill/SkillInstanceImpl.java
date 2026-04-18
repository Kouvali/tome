package org.kouv.tome.impl.skill;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

public final class SkillInstanceImpl<S> implements SkillInstance<S> {
    private final Holder<? extends Skill<S>> skill;
    private final LivingEntity source;
    private S state;

    private final AttributeModifierTracker attributeModifierTracker;
    private int duration;

    private boolean markedForCompletion;
    private int elapsedTime;

    public SkillInstanceImpl(
            Holder<? extends Skill<S>> skill,
            LivingEntity source, S state,
            AttributeModifierTracker attributeModifierTracker,
            int duration
    ) {
        this.skill = Objects.requireNonNull(skill);
        this.source = Objects.requireNonNull(source);
        this.state = Objects.requireNonNull(state);
        this.attributeModifierTracker = Objects.requireNonNull(attributeModifierTracker);
        this.duration = duration;
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
    public S getState() {
        return state;
    }

    @Override
    public void setState(S state) {
        this.state = Objects.requireNonNull(state);
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
    public boolean isMarkedForCompletion() {
        return markedForCompletion;
    }

    @Override
    public void setMarkedForCompletion(boolean markedForCompletion) {
        this.markedForCompletion = markedForCompletion;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void update() {
        elapsedTime++;
    }

    @Override
    public String toString() {
        return "SkillInstanceImpl{" +
                "skill=" + skill +
                ", source=" + source +
                ", state=" + state +
                ", attributeModifierTracker=" + attributeModifierTracker +
                ", duration=" + duration +
                ", markedForCompletion=" + markedForCompletion +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
