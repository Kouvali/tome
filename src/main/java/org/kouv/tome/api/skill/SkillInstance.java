package org.kouv.tome.api.skill;

public interface SkillInstance<S> extends SkillContext<S> {
    S getState();
}
