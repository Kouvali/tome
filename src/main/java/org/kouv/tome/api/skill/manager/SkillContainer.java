package org.kouv.tome.api.skill.manager;

import org.kouv.tome.api.skill.Skill;

import java.util.Set;
import net.minecraft.core.Holder;

public interface SkillContainer {
    Set<? extends Holder<? extends Skill<?>>> getSkills();

    boolean hasSkill(Holder<? extends Skill<?>> skill);

    boolean addSkill(Holder<? extends Skill<?>> skill);

    boolean removeSkill(Holder<? extends Skill<?>> skill);
}
