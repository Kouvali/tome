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

inline fun <S : Any> skill(builderAction: Skill.Builder<S>.() -> Unit): Skill<S> =
    Skill.builder<S>().apply(builderAction).build()

inline fun <S : Any> Skill.Builder<S>.cancelBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    skillCancelBehavior(block).also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCancelBehavior(): SkillCancelBehavior<S> =
    noOpSkillCancelBehavior<S>().also { cancelBehavior = it }

inline fun <S : Any> Skill.Builder<S>.completeBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    skillCompleteBehavior(block).also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCompleteBehavior(): SkillCompleteBehavior<S> =
    noOpSkillCompleteBehavior<S>().also { completeBehavior = it }

inline fun <S : Any> Skill.Builder<S>.endBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    skillEndBehavior(block).also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpEndBehavior(): SkillEndBehavior<S> =
    noOpSkillEndBehavior<S>().also { endBehavior = it }

inline fun <S : Any> Skill.Builder<S>.interruptBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    skillInterruptBehavior(block).also { interruptBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpInterruptBehavior(): SkillInterruptBehavior<S> =
    noOpSkillInterruptBehavior<S>().also { interruptBehavior = it }

inline fun <S : Any> Skill.Builder<S>.startBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    skillStartBehavior(block).also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpStartBehavior(): SkillStartBehavior<S> =
    noOpSkillStartBehavior<S>().also { startBehavior = it }

inline fun <S : Any> Skill.Builder<S>.tickBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    skillTickBehavior(block).also { tickBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpTickBehavior(): SkillTickBehavior<S> =
    noOpSkillTickBehavior<S>().also { tickBehavior = it }

inline fun Skill.Builder<*>.addCallback(crossinline block: SkillContext<*>.() -> Unit): SkillAddCallback =
    skillAddCallback(block).also { addCallback = it }

fun Skill.Builder<*>.noOpAddCallback(): SkillAddCallback =
    noOpSkillAddCallback().also { addCallback = it }

inline fun Skill.Builder<*>.cooldownEndCallback(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownEndCallback =
    skillCooldownEndCallback(block).also { cooldownEndCallback = it }

fun Skill.Builder<*>.noOpCooldownEndCallback(): SkillCooldownEndCallback =
    noOpSkillCooldownEndCallback().also { cooldownEndCallback = it }

inline fun Skill.Builder<*>.cooldownStartCallback(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownStartCallback =
    skillCooldownStartCallback(block).also { cooldownStartCallback = it }

fun Skill.Builder<*>.noOpCooldownStartCallback(): SkillCooldownStartCallback =
    noOpSkillCooldownStartCallback().also { cooldownStartCallback = it }

inline fun Skill.Builder<*>.entityLoadCallback(crossinline block: SkillContext<*>.() -> Unit): SkillEntityLoadCallback =
    skillEntityLoadCallback(block).also { entityLoadCallback = it }

fun Skill.Builder<*>.noOpEntityLoadCallback(): SkillEntityLoadCallback =
    noOpSkillEntityLoadCallback().also { entityLoadCallback = it }

inline fun Skill.Builder<*>.entityUnloadCallback(crossinline block: SkillContext<*>.() -> Unit): SkillEntityUnloadCallback =
    skillEntityUnloadCallback(block).also { entityUnloadCallback = it }

fun Skill.Builder<*>.noOpEntityUnloadCallback(): SkillEntityUnloadCallback =
    noOpSkillEntityUnloadCallback().also { entityUnloadCallback = it }

inline fun Skill.Builder<*>.removeCallback(crossinline block: SkillContext<*>.() -> Unit): SkillRemoveCallback =
    skillRemoveCallback(block).also { removeCallback = it }

fun Skill.Builder<*>.noOpRemoveCallback(): SkillRemoveCallback =
    noOpSkillRemoveCallback().also { removeCallback = it }

inline fun Skill.Builder<*>.condition(crossinline block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }

fun Skill.Builder<*>.requireLearnedCondition(): SkillCondition =
    requireLearnedSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireNoCooldownCondition(): SkillCondition =
    requireNoCooldownSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireInGameCondition(): SkillCondition =
    requireInGameSkillCondition().also { condition = it }

fun Skill.Builder<*>.defaultCondition(): SkillCondition =
    defaultSkillCondition().also { condition = it }

inline fun <S : Any> Skill.Builder<S>.cancelPredicate(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    skillCancelPredicate(block).also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedCancelPredicate(): SkillCancelPredicate<S> =
    allowedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedCancelPredicate(): SkillCancelPredicate<S> =
    deniedSkillCancelPredicate<S>().also { cancelPredicate = it }

inline fun <S : Any> Skill.Builder<S>.interruptCondition(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptPredicate<S> =
    skillInterruptPredicate(block).also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedInterruptPredicate(): SkillInterruptPredicate<S> =
    allowedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedInterruptPredicate(): SkillInterruptPredicate<S> =
    deniedSkillInterruptPredicate<S>().also { interruptPredicate = it }

inline fun <S : Any> Skill.Builder<S>.stateFactory(crossinline block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    skillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.alwaysOkStateFactory(crossinline block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    alwaysOkSkillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.alwaysErrorStateFactory(crossinline block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    alwaysErrorSkillStateFactory<S>(block).also { stateFactory = it }