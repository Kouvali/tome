package org.kouv.tome.api.skill;

public interface SkillInstance<S> extends SkillContext<S> {
    S getState();

    int getElapsedTime();

    default boolean completeCasting() {
        return getSkillManager().completeCasting();
    }

    default boolean cancelCasting() {
        return getSkillManager().cancelCasting();
    }

    default boolean interruptCasting() {
        return getSkillManager().interruptCasting();
    }

    default boolean terminateCasting() {
        return getSkillManager().terminateCasting();
    }
}
