package org.kouv.tome.api.skill;

import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.action.SkillAction;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.registry.SkillRegistries;

import java.util.Objects;

public final class Skill {
    private final SkillAction action;
    private final SkillCondition condition;

    private @Nullable String translationKey;
    private @Nullable Text name;

    private Skill(
            SkillAction action,
            SkillCondition condition
    ) {
        this.action = Objects.requireNonNull(action);
        this.condition = Objects.requireNonNull(condition);
    }

    public static Builder builder() {
        return new Builder();
    }

    public SkillAction getAction() {
        return action;
    }

    public SkillCondition getCondition() {
        return condition;
    }

    public String getTranslationKey() {
        if (translationKey == null) {
            translationKey = Util.createTranslationKey("skill", SkillRegistries.SKILL.getId(this));
        }

        return translationKey;
    }

    public Text getName() {
        if (name == null) {
            name = Text.translatable(getTranslationKey());
        }

        return name;
    }

    public static final class Builder {
        private SkillAction action = SkillAction.alwaysSuccess();
        private SkillCondition condition = SkillCondition.defaultConditions();

        private Builder() {
        }

        public SkillAction getAction() {
            return action;
        }

        public Builder setAction(SkillAction action) {
            this.action = Objects.requireNonNull(action);
            return this;
        }

        public SkillCondition getCondition() {
            return condition;
        }

        public Builder setCondition(SkillCondition condition) {
            this.condition = Objects.requireNonNull(condition);
            return this;
        }

        public Skill build() {
            return new Skill(
                    action,
                    condition
            );
        }
    }
}
