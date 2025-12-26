package org.kouv.tome.api.skill;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.entity.attribute.AttributeModifierSet;
import org.kouv.tome.api.skill.behavior.*;
import org.kouv.tome.api.skill.callback.*;
import org.kouv.tome.api.skill.condition.SkillCondition;
import org.kouv.tome.api.skill.duration.SkillDurationProvider;
import org.kouv.tome.api.skill.predicate.SkillCancelPredicate;
import org.kouv.tome.api.skill.predicate.SkillInterruptPredicate;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.api.skill.state.SkillStateFactory;

import java.util.Objects;

public final class Skill<S> {
    private final AttributeModifierSet attributeModifierSet;
    private final SkillCancelBehavior<S> cancelBehavior;
    private final SkillCompleteBehavior<S> completeBehavior;
    private final SkillEndBehavior<S> endBehavior;
    private final SkillInterruptBehavior<S> interruptBehavior;
    private final SkillStartBehavior<S> startBehavior;
    private final SkillTickBehavior<S> tickBehavior;
    private final SkillAddedCallback addedCallback;
    private final SkillCooldownEndedCallback cooldownEndedCallback;
    private final SkillCooldownStartedCallback cooldownStartedCallback;
    private final SkillLoadedCallback loadedCallback;
    private final SkillRemovedCallback removedCallback;
    private final SkillCondition condition;
    private final SkillDurationProvider durationProvider;
    private final SkillCancelPredicate<S> cancelPredicate;
    private final SkillInterruptPredicate<S> interruptPredicate;
    private final SkillStateFactory<S> stateFactory;

    private @Nullable Identifier id;
    private @Nullable String translationKey;
    private @Nullable Text name;

    private Skill(
            AttributeModifierSet attributeModifierSet,
            SkillCancelBehavior<S> cancelBehavior,
            SkillCompleteBehavior<S> completeBehavior,
            SkillEndBehavior<S> endBehavior,
            SkillInterruptBehavior<S> interruptBehavior,
            SkillStartBehavior<S> startBehavior,
            SkillTickBehavior<S> tickBehavior,
            SkillAddedCallback addedCallback,
            SkillCooldownEndedCallback cooldownEndedCallback,
            SkillCooldownStartedCallback cooldownStartedCallback,
            SkillLoadedCallback loadedCallback,
            SkillRemovedCallback removedCallback,
            SkillCondition condition, SkillDurationProvider durationProvider,
            SkillCancelPredicate<S> cancelPredicate,
            SkillInterruptPredicate<S> interruptPredicate,
            SkillStateFactory<S> stateFactory
    ) {
        this.attributeModifierSet = Objects.requireNonNull(attributeModifierSet);
        this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
        this.completeBehavior = Objects.requireNonNull(completeBehavior);
        this.endBehavior = Objects.requireNonNull(endBehavior);
        this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
        this.startBehavior = Objects.requireNonNull(startBehavior);
        this.tickBehavior = Objects.requireNonNull(tickBehavior);
        this.addedCallback = Objects.requireNonNull(addedCallback);
        this.cooldownEndedCallback = Objects.requireNonNull(cooldownEndedCallback);
        this.cooldownStartedCallback = Objects.requireNonNull(cooldownStartedCallback);
        this.loadedCallback = Objects.requireNonNull(loadedCallback);
        this.removedCallback = Objects.requireNonNull(removedCallback);
        this.condition = Objects.requireNonNull(condition);
        this.durationProvider = Objects.requireNonNull(durationProvider);
        this.cancelPredicate = Objects.requireNonNull(cancelPredicate);
        this.interruptPredicate = Objects.requireNonNull(interruptPredicate);
        this.stateFactory = Objects.requireNonNull(stateFactory);
    }

    public static <S> Builder<S> builder() {
        return new Builder<>();
    }

    public AttributeModifierSet getAttributeModifierSet() {
        return attributeModifierSet;
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

    public SkillAddedCallback getAddedCallback() {
        return addedCallback;
    }

    public SkillCooldownEndedCallback getCooldownEndedCallback() {
        return cooldownEndedCallback;
    }

    public SkillCooldownStartedCallback getCooldownStartedCallback() {
        return cooldownStartedCallback;
    }

    public SkillLoadedCallback getLoadedCallback() {
        return loadedCallback;
    }

    public SkillRemovedCallback getRemovedCallback() {
        return removedCallback;
    }

    public SkillCondition getCondition() {
        return condition;
    }

    public SkillDurationProvider getDurationProvider() {
        return durationProvider;
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
        private AttributeModifierSet attributeModifierSet = AttributeModifierSet.empty();
        private SkillCancelBehavior<S> cancelBehavior = SkillCancelBehavior.noOp();
        private SkillCompleteBehavior<S> completeBehavior = SkillCompleteBehavior.noOp();
        private SkillEndBehavior<S> endBehavior = SkillEndBehavior.noOp();
        private SkillInterruptBehavior<S> interruptBehavior = SkillInterruptBehavior.noOp();
        private SkillStartBehavior<S> startBehavior = SkillStartBehavior.noOp();
        private SkillTickBehavior<S> tickBehavior = SkillTickBehavior.noOp();
        private SkillAddedCallback addedCallback = SkillAddedCallback.noOp();
        private SkillCooldownEndedCallback cooldownEndedCallback = SkillCooldownEndedCallback.noOp();
        private SkillCooldownStartedCallback cooldownStartedCallback = SkillCooldownStartedCallback.noOp();
        private SkillLoadedCallback loadedCallback = SkillLoadedCallback.noOp();
        private SkillRemovedCallback removedCallback = SkillRemovedCallback.noOp();
        private SkillCondition condition = SkillCondition.defaultConditions();
        private SkillDurationProvider durationProvider = SkillDurationProvider.instant();
        private SkillCancelPredicate<S> cancelPredicate = SkillCancelPredicate.allowed();
        private SkillInterruptPredicate<S> interruptPredicate = SkillInterruptPredicate.allowed();
        private @Nullable SkillStateFactory<S> stateFactory = null;

        private Builder() {
        }

        public AttributeModifierSet getAttributeModifierSet() {
            return attributeModifierSet;
        }

        public Builder<S> setAttributeModifierSet(AttributeModifierSet attributeModifierSet) {
            this.attributeModifierSet = Objects.requireNonNull(attributeModifierSet);
            return this;
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

        public SkillAddedCallback getAddedCallback() {
            return addedCallback;
        }

        public Builder<S> setAddedCallback(SkillAddedCallback addedCallback) {
            this.addedCallback = Objects.requireNonNull(addedCallback);
            return this;
        }

        public SkillCooldownEndedCallback getCooldownEndedCallback() {
            return cooldownEndedCallback;
        }

        public Builder<S> setCooldownEndedCallback(SkillCooldownEndedCallback cooldownEndedCallback) {
            this.cooldownEndedCallback = Objects.requireNonNull(cooldownEndedCallback);
            return this;
        }

        public SkillCooldownStartedCallback getCooldownStartedCallback() {
            return cooldownStartedCallback;
        }

        public Builder<S> setCooldownStartedCallback(SkillCooldownStartedCallback cooldownStartedCallback) {
            this.cooldownStartedCallback = Objects.requireNonNull(cooldownStartedCallback);
            return this;
        }

        public SkillLoadedCallback getLoadedCallback() {
            return loadedCallback;
        }

        public Builder<S> setLoadedCallback(SkillLoadedCallback loadedCallback) {
            this.loadedCallback = Objects.requireNonNull(loadedCallback);
            return this;
        }

        public SkillRemovedCallback getRemovedCallback() {
            return removedCallback;
        }

        public Builder<S> setRemovedCallback(SkillRemovedCallback removedCallback) {
            this.removedCallback = Objects.requireNonNull(removedCallback);
            return this;
        }

        public SkillCondition getCondition() {
            return condition;
        }

        public Builder<S> setCondition(SkillCondition condition) {
            this.condition = Objects.requireNonNull(condition);
            return this;
        }

        public SkillDurationProvider getDurationProvider() {
            return durationProvider;
        }

        public Builder<S> setDurationProvider(SkillDurationProvider durationProvider) {
            this.durationProvider = Objects.requireNonNull(durationProvider);
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

        public Skill<S> build() {
            return new Skill<>(
                    attributeModifierSet,
                    cancelBehavior,
                    completeBehavior,
                    endBehavior,
                    interruptBehavior,
                    startBehavior,
                    tickBehavior,
                    addedCallback,
                    cooldownEndedCallback,
                    cooldownStartedCallback,
                    loadedCallback,
                    removedCallback,
                    condition,
                    durationProvider,
                    cancelPredicate,
                    interruptPredicate,
                    Objects.requireNonNull(stateFactory)
            );
        }
    }
}
