package org.kouv.tome.api.skill;

import org.kouv.tome.api.entity.attribute.AttributeModifierController;

public interface SkillInstance<S> extends SkillContext<S> {
    S getState();

    AttributeModifierController getAttributeModifierController();

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
