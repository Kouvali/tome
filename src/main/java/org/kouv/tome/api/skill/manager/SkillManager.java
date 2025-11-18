package org.kouv.tome.api.skill.manager;

import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillInstance;
import org.kouv.tome.api.skill.SkillResponse;

public interface SkillManager {
    boolean isCasting();

    <S> @Nullable SkillInstance<S> getCastingInstance(RegistryEntry<? extends Skill<S>> skill);

    SkillResponse testSkill(RegistryEntry<? extends Skill<?>> skill);

    SkillResponse castSkill(RegistryEntry<? extends Skill<?>> skill);

    boolean cancelCasting();

    boolean interruptCasting();

    boolean terminateCasting();
}
