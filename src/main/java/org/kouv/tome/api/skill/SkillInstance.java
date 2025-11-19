package org.kouv.tome.api.skill;

public interface SkillInstance<S> extends SkillContext<S> {
    S getState();

    boolean isShouldComplete();

    void setShouldComplete(boolean shouldComplete);

    int getTotalDuration();

    void setTotalDuration(int totalDuration);

    int getElapsedTime();
}
