package org.kouv.tome.api.skill.entity;

import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.manager.SkillManager;

public interface SkillEntity {
    default SkillContainer getSkillContainer() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    default SkillCooldownManager getSkillCooldownManager() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    default SkillManager getSkillManager() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
}
