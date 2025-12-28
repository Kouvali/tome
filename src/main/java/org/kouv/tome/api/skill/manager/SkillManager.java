package org.kouv.tome.api.skill.manager;

import net.minecraft.core.Holder;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillInstance;
import org.kouv.tome.api.skill.SkillResponse;

public interface SkillManager {
    boolean isCasting();

    <S> @Nullable SkillInstance<S> getCastingInstance(Holder<? extends Skill<S>> skill);

    SkillResponse testSkill(Holder<? extends Skill<?>> skill);

    SkillResponse castSkill(Holder<? extends Skill<?>> skill);

    boolean cancelCasting();

    boolean interruptCasting();

    boolean terminateCasting();
}
