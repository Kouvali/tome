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

fun Skill.Builder<*>.addCallback(block: SkillContext<*>.() -> Unit): SkillAddCallback =
    skillAddCallback(block).also { addCallback = it }

fun Skill.Builder<*>.noOpAddCallback(): SkillAddCallback =
    noOpSkillAddCallback().also { addCallback = it }

fun Skill.Builder<*>.cooldownEndCallback(block: SkillContext<*>.() -> Unit): SkillCooldownEndCallback =
    skillCooldownEndCallback(block).also { cooldownEndCallback = it }

fun Skill.Builder<*>.noOpCooldownEndCallback(): SkillCooldownEndCallback =
    noOpSkillCooldownEndCallback().also { cooldownEndCallback = it }

fun Skill.Builder<*>.cooldownStartCallback(block: SkillContext<*>.() -> Unit): SkillCooldownStartCallback =
    skillCooldownStartCallback(block).also { cooldownStartCallback = it }

fun Skill.Builder<*>.noOpCooldownStartCallback(): SkillCooldownStartCallback =
    noOpSkillCooldownStartCallback().also { cooldownStartCallback = it }

fun Skill.Builder<*>.entityLoadCallback(block: SkillContext<*>.() -> Unit): SkillEntityLoadCallback =
    skillEntityLoadCallback(block).also { entityLoadCallback = it }

fun Skill.Builder<*>.noOpEntityLoadCallback(): SkillEntityLoadCallback =
    noOpSkillEntityLoadCallback().also { entityLoadCallback = it }

fun Skill.Builder<*>.entityUnloadCallback(block: SkillContext<*>.() -> Unit): SkillEntityUnloadCallback =
    skillEntityUnloadCallback(block).also { entityUnloadCallback = it }

fun Skill.Builder<*>.noOpEntityUnloadCallback(): SkillEntityUnloadCallback =
    noOpSkillEntityUnloadCallback().also { entityUnloadCallback = it }

fun Skill.Builder<*>.removeCallback(block: SkillContext<*>.() -> Unit): SkillRemoveCallback =
    skillRemoveCallback(block).also { removeCallback = it }

fun Skill.Builder<*>.noOpRemoveCallback(): SkillRemoveCallback =
    noOpSkillRemoveCallback().also { removeCallback = it }

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