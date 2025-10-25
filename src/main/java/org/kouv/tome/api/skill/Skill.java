package org.kouv.tome.api.skill;

import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.behavior.*;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.duration.SkillDurationProvider;
import org.kouv.tome.api.skill.handler.SkillCancelHandler;
import org.kouv.tome.api.skill.handler.SkillInterruptHandler;
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
    private final SkillCondition condition;
    private final SkillDurationProvider durationProvider;
    private final SkillCancelHandler<S> cancelHandler;
    private final SkillInterruptHandler<S> interruptHandler;
    private final SkillStateFactory<S> stateFactory;

    private @Nullable String translationKey;
    private @Nullable Text name;

    private Skill(
            SkillCancelBehavior<S> cancelBehavior,
            SkillCompleteBehavior<S> completeBehavior,
            SkillEndBehavior<S> endBehavior,
            SkillInterruptBehavior<S> interruptBehavior,
            SkillStartBehavior<S> startBehavior,
            SkillTickBehavior<S> tickBehavior,
            SkillCondition condition,
            SkillDurationProvider durationProvider,
            SkillCancelHandler<S> cancelHandler,
            SkillInterruptHandler<S> interruptHandler,
            SkillStateFactory<S> stateFactory
    ) {
        this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
        this.completeBehavior = Objects.requireNonNull(completeBehavior);
        this.endBehavior = Objects.requireNonNull(endBehavior);
        this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
        this.startBehavior = Objects.requireNonNull(startBehavior);
        this.tickBehavior = Objects.requireNonNull(tickBehavior);
        this.condition = Objects.requireNonNull(condition);
        this.durationProvider = Objects.requireNonNull(durationProvider);
        this.cancelHandler = Objects.requireNonNull(cancelHandler);
        this.interruptHandler = Objects.requireNonNull(interruptHandler);
        this.stateFactory = Objects.requireNonNull(stateFactory);
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

    public SkillCondition getCondition() {
        return condition;
    }

    public SkillDurationProvider getDurationProvider() {
        return durationProvider;
    }

    public SkillCancelHandler<S> getCancelHandler() {
        return cancelHandler;
    }

    public SkillInterruptHandler<S> getInterruptHandler() {
        return interruptHandler;
    }

    public SkillStateFactory<S> getStateFactory() {
        return stateFactory;
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

    public static final class Builder<S> {
        private SkillCancelBehavior<S> cancelBehavior = SkillCancelBehavior.noOp();
        private SkillCompleteBehavior<S> completeBehavior = SkillCompleteBehavior.noOp();
        private SkillEndBehavior<S> endBehavior = SkillEndBehavior.noOp();
        private SkillInterruptBehavior<S> interruptBehavior = SkillInterruptBehavior.noOp();
        private SkillStartBehavior<S> startBehavior = SkillStartBehavior.noOp();
        private SkillTickBehavior<S> tickBehavior = SkillTickBehavior.noOp();
        private SkillCondition condition = SkillCondition.defaultConditions();
        private @Nullable SkillDurationProvider durationProvider = null;
        private SkillCancelHandler<S> cancelHandler = SkillCancelHandler.alwaysAllow();
        private SkillInterruptHandler<S> interruptHandler = SkillInterruptHandler.alwaysAllow();
        private @Nullable SkillStateFactory<S> stateFactory = null;

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

        public SkillCondition getCondition() {
            return condition;
        }

        public Builder<S> setCondition(SkillCondition condition) {
            this.condition = Objects.requireNonNull(condition);
            return this;
        }

        public @Nullable SkillDurationProvider getDurationProvider() {
            return durationProvider;
        }

        public Builder<S> setDurationProvider(SkillDurationProvider durationProvider) {
            this.durationProvider = Objects.requireNonNull(durationProvider);
            return this;
        }

        public SkillCancelHandler<S> getCancelHandler() {
            return cancelHandler;
        }

        public Builder<S> setCancelHandler(SkillCancelHandler<S> cancelHandler) {
            this.cancelHandler = Objects.requireNonNull(cancelHandler);
            return this;
        }

        public SkillInterruptHandler<S> getInterruptHandler() {
            return interruptHandler;
        }

        public Builder<S> setInterruptHandler(SkillInterruptHandler<S> interruptHandler) {
            this.interruptHandler = Objects.requireNonNull(interruptHandler);
            return this;
        }

        public @Nullable SkillStateFactory<S> getStateFactory() {
            return stateFactory;
        }

        public Builder<S> setStateFactory(SkillStateFactory<S> stateFactory) {
            this.stateFactory = Objects.requireNonNull(stateFactory);
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
                    condition,
                    Objects.requireNonNull(durationProvider),
                    cancelHandler,
                    interruptHandler,
                    Objects.requireNonNull(stateFactory)
            );
        }
    }
}
