package org.kouv.tome.api.skill

import org.kouv.tome.api.skill.behavior.*
import org.kouv.tome.api.skill.condition.*
import org.kouv.tome.api.skill.state.*

inline fun <S : Any> skill(builderAction: Skill.Builder<S>.() -> Unit): Skill<S> =
    Skill.builder<S>().apply(builderAction).build()

inline fun Skill.Builder<*>.addBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillAddBehavior =
    skillAddBehavior(block).also { addBehavior = it }

fun Skill.Builder<*>.noOpAddBehavior(): SkillAddBehavior =
    noOpSkillAddBehavior().also { addBehavior = it }

inline fun <S : Any> Skill.Builder<S>.cancelBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    skillCancelBehavior(block).also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCancelBehavior(): SkillCancelBehavior<S> =
    noOpSkillCancelBehavior<S>().also { cancelBehavior = it }

inline fun <S : Any> Skill.Builder<S>.completeBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    skillCompleteBehavior(block).also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCompleteBehavior(): SkillCompleteBehavior<S> =
    noOpSkillCompleteBehavior<S>().also { completeBehavior = it }

inline fun Skill.Builder<*>.cooldownEndBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownEndBehavior =
    skillCooldownEndBehavior(block).also { cooldownEndBehavior = it }

fun Skill.Builder<*>.noOpCooldownEndBehavior(): SkillCooldownEndBehavior =
    noOpSkillCooldownEndBehavior().also { cooldownEndBehavior = it }

inline fun Skill.Builder<*>.cooldownStartBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownStartBehavior =
    skillCooldownStartBehavior(block).also { cooldownStartBehavior = it }

fun Skill.Builder<*>.noOpCooldownStartBehavior(): SkillCooldownStartBehavior =
    noOpSkillCooldownStartBehavior().also { cooldownStartBehavior = it }

inline fun <S : Any> Skill.Builder<S>.endBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    skillEndBehavior(block).also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpEndBehavior(): SkillEndBehavior<S> =
    noOpSkillEndBehavior<S>().also { endBehavior = it }

inline fun Skill.Builder<*>.entityLoadBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillEntityLoadBehavior =
    skillEntityLoadBehavior(block).also { entityLoadBehavior = it }

fun Skill.Builder<*>.noOpEntityLoadBehavior(): SkillEntityLoadBehavior =
    noOpSkillEntityLoadBehavior().also { entityLoadBehavior = it }

inline fun Skill.Builder<*>.entityUnloadBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillEntityUnloadBehavior =
    skillEntityUnloadBehavior(block).also { entityUnloadBehavior = it }

fun Skill.Builder<*>.noOpEntityUnloadBehavior(): SkillEntityUnloadBehavior =
    noOpSkillEntityUnloadBehavior().also { entityUnloadBehavior = it }

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

inline fun <S : Any> Skill.Builder<S>.cancelCondition(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelCondition<S> =
    skillCancelCondition(block).also { cancelCondition = it }

fun <S : Any> Skill.Builder<S>.allowedCancelCondition(): SkillCancelCondition<S> =
    allowedSkillCancelCondition<S>().also { cancelCondition = it }

fun <S : Any> Skill.Builder<S>.deniedCancelCondition(): SkillCancelCondition<S> =
    deniedSkillCancelCondition<S>().also { cancelCondition = it }

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

inline fun <S : Any> Skill.Builder<S>.interruptCondition(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptCondition<S> =
    skillInterruptCondition(block).also { interruptCondition = it }

fun <S : Any> Skill.Builder<S>.allowedInterruptCondition(): SkillInterruptCondition<S> =
    allowedSkillInterruptCondition<S>().also { interruptCondition = it }

fun <S : Any> Skill.Builder<S>.deniedInterruptCondition(): SkillInterruptCondition<S> =
    deniedSkillInterruptCondition<S>().also { interruptCondition = it }

inline fun <S : Any> Skill.Builder<S>.stateFactory(crossinline block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    skillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.alwaysOkStateFactory(crossinline block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    alwaysOkSkillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.alwaysErrorStateFactory(crossinline block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    alwaysErrorSkillStateFactory<S>(block).also { stateFactory = it }