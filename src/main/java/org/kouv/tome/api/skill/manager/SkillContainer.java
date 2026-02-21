package org.kouv.tome.api.skill.manager;

import net.minecraft.core.Holder;
import org.kouv.tome.api.skill.Skill;

import java.util.Collection;
import java.util.Set;

public interface SkillContainer {
    Set<? extends Holder<? extends Skill<?>>> getSkills();

    boolean hasSkill(Holder<? extends Skill<?>> skill);

    boolean hasSkills(Collection<? extends Holder<? extends Skill<?>>> skills);

    boolean addSkill(Holder<? extends Skill<?>> skill);

    boolean addSkills(Collection<? extends Holder<? extends Skill<?>>> skills);

    boolean removeSkill(Holder<? extends Skill<?>> skill);

    boolean removeSkills(Collection<? extends Holder<? extends Skill<?>>> skills);
}
