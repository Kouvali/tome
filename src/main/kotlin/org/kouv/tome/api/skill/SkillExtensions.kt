package org.kouv.tome.api.skill

import org.kouv.tome.api.skill.behavior.*
import org.kouv.tome.api.skill.callback.*
import org.kouv.tome.api.skill.condition.*
import org.kouv.tome.api.skill.predicate.SkillCancelPredicate
import org.kouv.tome.api.skill.predicate.SkillInterruptPredicate
import org.kouv.tome.api.skill.predicate.allowedSkillCancelPredicate
import org.kouv.tome.api.skill.predicate.allowedSkillInterruptPredicate
import org.kouv.tome.api.skill.predicate.deniedSkillCancelPredicate
import org.kouv.tome.api.skill.predicate.deniedSkillInterruptPredicate
import org.kouv.tome.api.skill.predicate.skillCancelPredicate
import org.kouv.tome.api.skill.predicate.skillInterruptPredicate
import org.kouv.tome.api.skill.state.*

fun <S : Any> skill(builderAction: Skill.Builder<S>.() -> Unit): Skill<S> =
    Skill.builder<S>().apply(builderAction).build()

fun <S : Any> Skill.Builder<S>.cancelBehavior(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    skillCancelBehavior(block).also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCancelBehavior(): SkillCancelBehavior<S> =
    noOpSkillCancelBehavior<S>().also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.completeBehavior(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    skillCompleteBehavior(block).also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCompleteBehavior(): SkillCompleteBehavior<S> =
    noOpSkillCompleteBehavior<S>().also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.endBehavior(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    skillEndBehavior(block).also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpEndBehavior(): SkillEndBehavior<S> =
    noOpSkillEndBehavior<S>().also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.interruptBehavior(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    skillInterruptBehavior(block).also { interruptBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpInterruptBehavior(): SkillInterruptBehavior<S> =
    noOpSkillInterruptBehavior<S>().also { interruptBehavior = it }

fun <S : Any> Skill.Builder<S>.startBehavior(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    skillStartBehavior(block).also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpStartBehavior(): SkillStartBehavior<S> =
    noOpSkillStartBehavior<S>().also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.tickBehavior(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    skillTickBehavior(block).also { tickBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpTickBehavior(): SkillTickBehavior<S> =
    noOpSkillTickBehavior<S>().also { tickBehavior = it }

fun Skill.Builder<*>.addedCallback(block: SkillContext<*>.() -> Unit): SkillAddedCallback =
    skillAddedCallback(block).also { addedCallback = it }

fun Skill.Builder<*>.noOpAddedCallback(): SkillAddedCallback =
    noOpSkillAddedCallback().also { addedCallback = it }

fun Skill.Builder<*>.cooldownEndedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownEndedCallback =
    skillCooldownEndedCallback(block).also { cooldownEndedCallback = it }

fun Skill.Builder<*>.noOpCooldownEndedCallback(): SkillCooldownEndedCallback =
    noOpSkillCooldownEndedCallback().also { cooldownEndedCallback = it }

fun Skill.Builder<*>.cooldownStartedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownStartedCallback =
    skillCooldownStartedCallback(block).also { cooldownStartedCallback = it }

fun Skill.Builder<*>.noOpCooldownStartedCallback(): SkillCooldownStartedCallback =
    noOpSkillCooldownStartedCallback().also { cooldownStartedCallback = it }

fun Skill.Builder<*>.loadedCallback(block: SkillContext<*>.() -> Unit): SkillLoadedCallback =
    skillLoadedCallback(block).also { loadedCallback = it }

fun Skill.Builder<*>.noOpLoadedCallback(): SkillLoadedCallback =
    noOpSkillLoadedCallback().also { loadedCallback = it }

fun Skill.Builder<*>.removedCallback(block: SkillContext<*>.() -> Unit): SkillRemovedCallback =
    skillRemovedCallback(block).also { removedCallback = it }

fun Skill.Builder<*>.noOpRemovedCallback(): SkillRemovedCallback =
    noOpSkillRemovedCallback().also { removedCallback = it }

fun Skill.Builder<*>.condition(block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }

fun Skill.Builder<*>.requireLearnedCondition(): SkillCondition =
    requireLearnedSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireNoCooldownCondition(): SkillCondition =
    requireNoCooldownSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireInGameCondition(): SkillCondition =
    requireInGameSkillCondition().also { condition = it }

fun Skill.Builder<*>.defaultCondition(): SkillCondition =
    defaultSkillCondition().also { condition = it }

fun <S : Any> Skill.Builder<S>.cancelPredicate(block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    skillCancelPredicate(block).also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedCancelPredicate(): SkillCancelPredicate<S> =
    allowedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedCancelPredicate(): SkillCancelPredicate<S> =
    deniedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.interruptCondition(block: SkillInstance<out S>.() -> Boolean): SkillInterruptPredicate<S> =
    skillInterruptPredicate(block).also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedInterruptPredicate(): SkillInterruptPredicate<S> =
    allowedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedInterruptPredicate(): SkillInterruptPredicate<S> =
    deniedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.stateFactory(block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    skillStateFactory(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.alwaysOkStateFactory(block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    alwaysOkSkillStateFactory(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.alwaysErrorStateFactory(block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    alwaysErrorSkillStateFactory<S>(block).also { stateFactory = it }