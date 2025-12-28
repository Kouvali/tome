package org.kouv.tome.api.skill;

import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;

public interface SkillInstance<S> extends SkillContext<S> {
    S getState();

    void setState(S state);

    AttributeModifierTracker getAttributeModifierTracker();

    int getDuration();

    void setDuration(int duration);

    boolean isShouldComplete();

    void setShouldComplete(boolean shouldComplete);

    int getElapsedTime();

    default int getRemainingDuration() {
        int duration = getDuration();
        return duration < 0 ?
                -1 :
                Math.max(0, duration - getElapsedTime());
    }

    default float getProgress() {
        int duration = getDuration();
        return duration <= 0 ?
                0.0f :
                Math.clamp((float) getElapsedTime() / duration, 0.0f, 1.0f);
    }
}
