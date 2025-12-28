package org.kouv.tome.api.skill.manager;

import net.minecraft.core.Holder;
import org.kouv.tome.api.skill.Skill;

import java.util.Set;

public interface SkillContainer {
    Set<? extends Holder<? extends Skill<?>>> getSkills();

    boolean hasSkill(Holder<? extends Skill<?>> skill);

    boolean addSkill(Holder<? extends Skill<?>> skill);

    boolean removeSkill(Holder<? extends Skill<?>> skill);
}
