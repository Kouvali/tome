package org.kouv.tome.api.skill;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.behavior.*;
import org.kouv.tome.api.skill.callback.*;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.predicate.SkillCancelPredicate;
import org.kouv.tome.api.skill.predicate.SkillInterruptPredicate;
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
    private final SkillAddCallback addCallback;
    private final SkillCooldownEndCallback cooldownEndCallback;
    private final SkillCooldownStartCallback cooldownStartCallback;
    private final SkillEntityLoadCallback entityLoadCallback;
    private final SkillEntityUnloadCallback entityUnloadCallback;
    private final SkillRemoveCallback removeCallback;
    private final SkillCondition condition;
    private final SkillCancelPredicate<S> cancelPredicate;
    private final SkillInterruptPredicate<S> interruptPredicate;
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
            SkillAddCallback addCallback,
            SkillCooldownEndCallback cooldownEndCallback,
            SkillCooldownStartCallback cooldownStartCallback,
            SkillEntityLoadCallback entityLoadCallback,
            SkillEntityUnloadCallback entityUnloadCallback,
            SkillRemoveCallback removeCallback,
            SkillCondition condition,
            SkillCancelPredicate<S> cancelPredicate,
            SkillInterruptPredicate<S> interruptPredicate,
            SkillStateFactory<S> stateFactory,
            int totalDuration
    ) {
        this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
        this.completeBehavior = Objects.requireNonNull(completeBehavior);
        this.endBehavior = Objects.requireNonNull(endBehavior);
        this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
        this.startBehavior = Objects.requireNonNull(startBehavior);
        this.tickBehavior = Objects.requireNonNull(tickBehavior);
        this.addCallback = Objects.requireNonNull(addCallback);
        this.cooldownEndCallback = Objects.requireNonNull(cooldownEndCallback);
        this.cooldownStartCallback = Objects.requireNonNull(cooldownStartCallback);
        this.entityLoadCallback = Objects.requireNonNull(entityLoadCallback);
        this.entityUnloadCallback = Objects.requireNonNull(entityUnloadCallback);
        this.removeCallback = Objects.requireNonNull(removeCallback);
        this.condition = Objects.requireNonNull(condition);
        this.cancelPredicate = Objects.requireNonNull(cancelPredicate);
        this.interruptPredicate = Objects.requireNonNull(interruptPredicate);
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

    public SkillAddCallback getAddCallback() {
        return addCallback;
    }

    public SkillCooldownEndCallback getCooldownEndCallback() {
        return cooldownEndCallback;
    }

    public SkillCooldownStartCallback getCooldownStartCallback() {
        return cooldownStartCallback;
    }

    public SkillEntityLoadCallback getEntityLoadCallback() {
        return entityLoadCallback;
    }

    public SkillEntityUnloadCallback getEntityUnloadCallback() {
        return entityUnloadCallback;
    }

    public SkillRemoveCallback getRemoveCallback() {
        return removeCallback;
    }

    public SkillCondition getCondition() {
        return condition;
    }

    public SkillCancelPredicate<S> getCancelPredicate() {
        return cancelPredicate;
    }

    public SkillInterruptPredicate<S> getInterruptPredicate() {
        return interruptPredicate;
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
        private SkillAddCallback addCallback = SkillAddCallback.noOp();
        private SkillCooldownEndCallback cooldownEndCallback = SkillCooldownEndCallback.noOp();
        private SkillCooldownStartCallback cooldownStartCallback = SkillCooldownStartCallback.noOp();
        private SkillEntityLoadCallback entityLoadCallback = SkillEntityLoadCallback.noOp();
        private SkillEntityUnloadCallback entityUnloadCallback = SkillEntityUnloadCallback.noOp();
        private SkillRemoveCallback removeCallback = SkillRemoveCallback.noOp();
        private SkillCondition condition = SkillCondition.defaultConditions();
        private SkillCancelPredicate<S> cancelPredicate = SkillCancelPredicate.allowed();
        private SkillInterruptPredicate<S> interruptPredicate = SkillInterruptPredicate.allowed();
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

        public SkillAddCallback getAddCallback() {
            return addCallback;
        }

        public Builder<S> setAddCallback(SkillAddCallback addCallback) {
            this.addCallback = Objects.requireNonNull(addCallback);
            return this;
        }

        public SkillCooldownEndCallback getCooldownEndCallback() {
            return cooldownEndCallback;
        }

        public Builder<S> setCooldownEndCallback(SkillCooldownEndCallback cooldownEndCallback) {
            this.cooldownEndCallback = Objects.requireNonNull(cooldownEndCallback);
            return this;
        }

        public SkillCooldownStartCallback getCooldownStartCallback() {
            return cooldownStartCallback;
        }

        public Builder<S> setCooldownStartCallback(SkillCooldownStartCallback cooldownStartCallback) {
            this.cooldownStartCallback = Objects.requireNonNull(cooldownStartCallback);
            return this;
        }

        public SkillEntityLoadCallback getEntityLoadCallback() {
            return entityLoadCallback;
        }

        public Builder<S> setEntityLoadCallback(SkillEntityLoadCallback entityLoadCallback) {
            this.entityLoadCallback = Objects.requireNonNull(entityLoadCallback);
            return this;
        }

        public SkillEntityUnloadCallback getEntityUnloadCallback() {
            return entityUnloadCallback;
        }

        public Builder<S> setEntityUnloadCallback(SkillEntityUnloadCallback entityUnloadCallback) {
            this.entityUnloadCallback = Objects.requireNonNull(entityUnloadCallback);
            return this;
        }

        public SkillRemoveCallback getRemoveCallback() {
            return removeCallback;
        }

        public Builder<S> setRemoveCallback(SkillRemoveCallback removeCallback) {
            this.removeCallback = Objects.requireNonNull(removeCallback);
            return this;
        }

        public SkillCondition getCondition() {
            return condition;
        }

        public Builder<S> setCondition(SkillCondition condition) {
            this.condition = Objects.requireNonNull(condition);
            return this;
        }

        public SkillCancelPredicate<S> getCancelPredicate() {
            return cancelPredicate;
        }

        public Builder<S> setCancelPredicate(SkillCancelPredicate<S> cancelPredicate) {
            this.cancelPredicate = Objects.requireNonNull(cancelPredicate);
            return this;
        }

        public SkillInterruptPredicate<S> getInterruptPredicate() {
            return interruptPredicate;
        }

        public Builder<S> setInterruptPredicate(SkillInterruptPredicate<S> interruptPredicate) {
            this.interruptPredicate = Objects.requireNonNull(interruptPredicate);
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
                    addCallback,
                    cooldownEndCallback,
                    cooldownStartCallback,
                    entityLoadCallback,
                    entityUnloadCallback,
                    removeCallback,
                    condition,
                    cancelPredicate,
                    interruptPredicate,
                    Objects.requireNonNull(stateFactory),
                    totalDuration
            );
        }
    }
}
