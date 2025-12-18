package org.kouv.tome.api.skill

import org.kouv.tome.api.entity.attribute.AttributeModifierSet
import org.kouv.tome.api.skill.behavior.*
import org.kouv.tome.api.skill.callback.*
import org.kouv.tome.api.skill.condition.*
import org.kouv.tome.api.skill.predicate.*
import org.kouv.tome.api.skill.state.*

fun <S : Any> Skill(builderAction: Skill.Builder<S>.() -> Unit): Skill<S> =
    Skill.builder<S>().apply(builderAction).build()

fun Skill.Builder<*>.attributeModifierSet(builderAction: AttributeModifierSet.Builder.() -> Unit): AttributeModifierSet =
    AttributeModifierSet(builderAction).also { attributeModifierSet = it }

fun <S : Any> Skill.Builder<S>.cancelBehavior(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    SkillCancelBehavior(block).also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCancelBehavior(): SkillCancelBehavior<S> =
    NoOpSkillCancelBehavior<S>().also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.completeBehavior(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    SkillCompleteBehavior(block).also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCompleteBehavior(): SkillCompleteBehavior<S> =
    NoOpSkillCompleteBehavior<S>().also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.endBehavior(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    SkillEndBehavior(block).also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpEndBehavior(): SkillEndBehavior<S> =
    NoOpSkillEndBehavior<S>().also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.interruptBehavior(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    SkillInterruptBehavior(block).also { interruptBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpInterruptBehavior(): SkillInterruptBehavior<S> =
    NoOpSkillInterruptBehavior<S>().also { interruptBehavior = it }

fun <S : Any> Skill.Builder<S>.startBehavior(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    SkillStartBehavior(block).also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpStartBehavior(): SkillStartBehavior<S> =
    NoOpSkillStartBehavior<S>().also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.tickBehavior(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    SkillTickBehavior(block).also { tickBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpTickBehavior(): SkillTickBehavior<S> =
    NoOpSkillTickBehavior<S>().also { tickBehavior = it }

fun Skill.Builder<*>.addedCallback(block: SkillContext<*>.() -> Unit): SkillAddedCallback =
    SkillAddedCallback(block).also { addedCallback = it }

fun Skill.Builder<*>.noOpAddedCallback(): SkillAddedCallback =
    NoOpSkillAddedCallback().also { addedCallback = it }

fun Skill.Builder<*>.cooldownEndedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownEndedCallback =
    SkillCooldownEndedCallback(block).also { cooldownEndedCallback = it }

fun Skill.Builder<*>.noOpCooldownEndedCallback(): SkillCooldownEndedCallback =
    NoOpSkillCooldownEndedCallback().also { cooldownEndedCallback = it }

fun Skill.Builder<*>.cooldownStartedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownStartedCallback =
    SkillCooldownStartedCallback(block).also { cooldownStartedCallback = it }

fun Skill.Builder<*>.noOpCooldownStartedCallback(): SkillCooldownStartedCallback =
    NoOpSkillCooldownStartedCallback().also { cooldownStartedCallback = it }

fun Skill.Builder<*>.loadedCallback(block: SkillContext<*>.() -> Unit): SkillLoadedCallback =
    SkillLoadedCallback(block).also { loadedCallback = it }

fun Skill.Builder<*>.noOpLoadedCallback(): SkillLoadedCallback =
    NoOpSkillLoadedCallback().also { loadedCallback = it }

fun Skill.Builder<*>.removedCallback(block: SkillContext<*>.() -> Unit): SkillRemovedCallback =
    SkillRemovedCallback(block).also { removedCallback = it }

fun Skill.Builder<*>.noOpRemovedCallback(): SkillRemovedCallback =
    NoOpSkillRemovedCallback().also { removedCallback = it }

fun Skill.Builder<*>.condition(block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    SkillCondition(block).also { condition = it }

fun Skill.Builder<*>.requireLearnedCondition(): SkillCondition =
    RequireLearnedSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireNoCooldownCondition(): SkillCondition =
    RequireNoCooldownSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireInGameCondition(): SkillCondition =
    RequireInGameSkillCondition().also { condition = it }

fun Skill.Builder<*>.defaultCondition(): SkillCondition =
    DefaultSkillCondition().also { condition = it }

fun <S : Any> Skill.Builder<S>.cancelPredicate(block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    SkillCancelPredicate(block).also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedCancelPredicate(): SkillCancelPredicate<S> =
    AllowedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedCancelPredicate(): SkillCancelPredicate<S> =
    DeniedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.interruptPredicate(block: SkillInstance<out S>.() -> Boolean): SkillInterruptPredicate<S> =
    SkillInterruptPredicate(block).also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedInterruptPredicate(): SkillInterruptPredicate<S> =
    AllowedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedInterruptPredicate(): SkillInterruptPredicate<S> =
    DeniedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.stateFactory(block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    SkillStateFactory(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.alwaysOkStateFactory(block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    AlwaysOkSkillStateFactory(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.alwaysErrorStateFactory(block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    AlwaysErrorSkillStateFactory<S>(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.constantStateFactory(state: S): SkillStateFactory<S> =
    ConstantSkillStateFactory(state).also { stateFactory = it }