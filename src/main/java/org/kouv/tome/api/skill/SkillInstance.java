package org.kouv.tome.api.skill;

import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;

public interface SkillInstance<S> extends SkillContext<S> {
    S getState();

    AttributeModifierTracker getAttributeModifierTracker();

    int getTotalDuration();

    void setTotalDuration(int totalDuration);

    boolean isShouldComplete();

    void setShouldComplete(boolean shouldComplete);

    int getElapsedTime();

    default int getRemainingDuration() {
        int total = getTotalDuration();
        return total < 0 ?
                -1 :
                Math.max(0, total - getElapsedTime());
    }

    default float getProgress() {
        int total = getTotalDuration();
        return total <= 0 ?
                0.0f :
                Math.clamp((float) getElapsedTime() / total, 0.0f, 1.0f);
    }
}
