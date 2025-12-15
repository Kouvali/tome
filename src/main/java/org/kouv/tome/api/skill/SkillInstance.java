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
}
