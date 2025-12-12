package org.kouv.tome.api.skill;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.behavior.*;
import org.kouv.tome.api.skill.condition.SkillCancelCondition;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.condition.SkillInterruptCondition;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.api.skill.state.SkillStateFactory;

import java.util.Objects;

public final class Skill<S> {
    private final SkillCancelBehavior<S> cancelBehavior;
    private final SkillCompleteBehavior<S> completeBehavior;
    private final SkillEndBehavior<S> endBehavior;
    private final SkillInterruptBehavior<S> interruptBehavior;
    private final SkillStartBehavior<S> startBehavior;
    private final SkillTickBehavior<S> tickBehavior;
    private final SkillCancelCondition<S> cancelCondition;
    private final SkillCondition condition;
    private final SkillInterruptCondition<S> interruptCondition;
    private final SkillStateFactory<S> stateFactory;
    private final int totalDuration;

    private @Nullable Identifier id;
    private @Nullable String translationKey;
    private @Nullable Text name;

    private Skill(
            SkillCancelBehavior<S> cancelBehavior,
            SkillCompleteBehavior<S> completeBehavior,
            SkillEndBehavior<S> endBehavior,
            SkillInterruptBehavior<S> interruptBehavior,
            SkillStartBehavior<S> startBehavior,
            SkillTickBehavior<S> tickBehavior,
            SkillCancelCondition<S> cancelCondition,
            SkillCondition condition,
            SkillInterruptCondition<S> interruptCondition,
            SkillStateFactory<S> stateFactory,
            int totalDuration
    ) {
        this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
        this.completeBehavior = Objects.requireNonNull(completeBehavior);
        this.endBehavior = Objects.requireNonNull(endBehavior);
        this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
        this.startBehavior = Objects.requireNonNull(startBehavior);
        this.tickBehavior = Objects.requireNonNull(tickBehavior);
        this.cancelCondition = Objects.requireNonNull(cancelCondition);
        this.condition = Objects.requireNonNull(condition);
        this.interruptCondition = Objects.requireNonNull(interruptCondition);
        this.stateFactory = Objects.requireNonNull(stateFactory);
        this.totalDuration = totalDuration;
    }

    public static <S> Builder<S> builder() {
        return new Builder<>();
    }

    public SkillCancelBehavior<S> getCancelBehavior() {
        return cancelBehavior;
    }

    public SkillCompleteBehavior<S> getCompleteBehavior() {
        return completeBehavior;
    }

    public SkillEndBehavior<S> getEndBehavior() {
        return endBehavior;
    }

    public SkillInterruptBehavior<S> getInterruptBehavior() {
        return interruptBehavior;
    }

    public SkillStartBehavior<S> getStartBehavior() {
        return startBehavior;
    }

    public SkillTickBehavior<S> getTickBehavior() {
        return tickBehavior;
    }

    public SkillCancelCondition<S> getCancelCondition() {
        return cancelCondition;
    }

    public SkillCondition getCondition() {
        return condition;
    }

    public SkillInterruptCondition<S> getInterruptCondition() {
        return interruptCondition;
    }

    public SkillStateFactory<S> getStateFactory() {
        return stateFactory;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public Identifier getId() {
        if (id == null) {
            id = SkillRegistries.SKILL.getId(this);
        }

        return id;
    }

    public String getTranslationKey() {
        if (translationKey == null) {
            translationKey = Util.createTranslationKey("skill", getId());
        }

        return translationKey;
    }

    public Text getName() {
        if (name == null) {
            name = Text.translatable(getTranslationKey());
        }

        return name;
    }

    public static final class Builder<S> {
        private SkillCancelBehavior<S> cancelBehavior = SkillCancelBehavior.noOp();
        private SkillCompleteBehavior<S> completeBehavior = SkillCompleteBehavior.noOp();
        private SkillEndBehavior<S> endBehavior = SkillEndBehavior.noOp();
        private SkillInterruptBehavior<S> interruptBehavior = SkillInterruptBehavior.noOp();
        private SkillStartBehavior<S> startBehavior = SkillStartBehavior.noOp();
        private SkillTickBehavior<S> tickBehavior = SkillTickBehavior.noOp();
        private SkillCancelCondition<S> cancelCondition = SkillCancelCondition.allowed();
        private SkillCondition condition = SkillCondition.defaultConditions();
        private SkillInterruptCondition<S> interruptCondition = SkillInterruptCondition.allowed();
        private @Nullable SkillStateFactory<S> stateFactory = null;
        private int totalDuration = 0;

        private Builder() {
        }

        public SkillCancelBehavior<S> getCancelBehavior() {
            return cancelBehavior;
        }

        public Builder<S> setCancelBehavior(SkillCancelBehavior<S> cancelBehavior) {
            this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
            return this;
        }

        public SkillCompleteBehavior<S> getCompleteBehavior() {
            return completeBehavior;
        }

        public Builder<S> setCompleteBehavior(SkillCompleteBehavior<S> completeBehavior) {
            this.completeBehavior = Objects.requireNonNull(completeBehavior);
            return this;
        }

        public SkillEndBehavior<S> getEndBehavior() {
            return endBehavior;
        }

        public Builder<S> setEndBehavior(SkillEndBehavior<S> endBehavior) {
            this.endBehavior = Objects.requireNonNull(endBehavior);
            return this;
        }

        public SkillInterruptBehavior<S> getInterruptBehavior() {
            return interruptBehavior;
        }

        public Builder<S> setInterruptBehavior(SkillInterruptBehavior<S> interruptBehavior) {
            this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
            return this;
        }

        public SkillStartBehavior<S> getStartBehavior() {
            return startBehavior;
        }

        public Builder<S> setStartBehavior(SkillStartBehavior<S> startBehavior) {
            this.startBehavior = Objects.requireNonNull(startBehavior);
            return this;
        }

        public SkillTickBehavior<S> getTickBehavior() {
            return tickBehavior;
        }

        public Builder<S> setTickBehavior(SkillTickBehavior<S> tickBehavior) {
            this.tickBehavior = Objects.requireNonNull(tickBehavior);
            return this;
        }

        public SkillCancelCondition<S> getCancelCondition() {
            return cancelCondition;
        }

        public Builder<S> setCancelCondition(SkillCancelCondition<S> cancelCondition) {
            this.cancelCondition = Objects.requireNonNull(cancelCondition);
            return this;
        }

        public SkillCondition getCondition() {
            return condition;
        }

        public Builder<S> setCondition(SkillCondition condition) {
            this.condition = Objects.requireNonNull(condition);
            return this;
        }

        public SkillInterruptCondition<S> getInterruptCondition() {
            return interruptCondition;
        }

        public Builder<S> setInterruptCondition(SkillInterruptCondition<S> interruptCondition) {
            this.interruptCondition = Objects.requireNonNull(interruptCondition);
            return this;
        }

        public @Nullable SkillStateFactory<S> getStateFactory() {
            return stateFactory;
        }

        public Builder<S> setStateFactory(SkillStateFactory<S> stateFactory) {
            this.stateFactory = Objects.requireNonNull(stateFactory);
            return this;
        }

        public int getTotalDuration() {
            return totalDuration;
        }

        public Builder<S> setTotalDuration(int totalDuration) {
            this.totalDuration = totalDuration;
            return this;
        }

        public Skill<S> build() {
            return new Skill<>(
                    cancelBehavior,
                    completeBehavior,
                    endBehavior,
                    interruptBehavior,
                    startBehavior,
                    tickBehavior,
                    cancelCondition,
                    condition,
                    interruptCondition,
                    Objects.requireNonNull(stateFactory),
                    totalDuration
            );
        }
    }
}
