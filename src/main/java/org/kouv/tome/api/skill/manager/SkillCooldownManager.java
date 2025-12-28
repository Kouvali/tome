package org.kouv.tome.api.skill.manager;

import net.minecraft.core.Holder;
import org.kouv.tome.api.skill.Skill;

public interface SkillCooldownManager {
    boolean isCoolingDown(Holder<? extends Skill<?>> skill);

    int getCooldown(Holder<? extends Skill<?>> skill);

    void setCooldown(Holder<? extends Skill<?>> skill, int cooldown);
}
