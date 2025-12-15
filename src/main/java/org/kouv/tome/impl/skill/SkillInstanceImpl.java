package org.kouv.tome.impl.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.entity.attribute.AttributeModifierController;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;

public final class SkillInstanceImpl<S> implements SkillInstance<S> {
    private final SkillContext<S> context;
    private final S state;

    private final AttributeModifierController attributeModifierController;
    private int totalDuration;

    private boolean shouldComplete;
    private int elapsedTime;

    public SkillInstanceImpl(SkillContext<S> context, S state) {
        this.context = Objects.requireNonNull(context);
        this.state = Objects.requireNonNull(state);
        this.attributeModifierController = new AttributeModifierController(getSource().getAttributes());
        this.totalDuration = getSkill().value().getTotalDuration();
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public AttributeModifierController getAttributeModifierController() {
        return attributeModifierController;
    }

    @Override
    public int getTotalDuration() {
        return totalDuration;
    }

    @Override
    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
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
    public RegistryEntry<? extends Skill<S>> getSkill() {
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
                ", totalDuration=" + totalDuration +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
