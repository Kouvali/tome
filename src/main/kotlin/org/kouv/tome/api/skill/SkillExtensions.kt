package org.kouv.tome.api.skill

import net.minecraft.registry.entry.RegistryEntry
import org.kouv.tome.api.skill.behavior.*
import org.kouv.tome.api.skill.condition.*
import org.kouv.tome.api.skill.duration.constantSkillDurationProvider
import org.kouv.tome.api.skill.duration.SkillDurationProvider
import org.kouv.tome.api.skill.duration.skillDurationProvider
import org.kouv.tome.api.skill.handler.*
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

inline fun <S : Any> Skill.Builder<S>.condition(crossinline block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }

inline fun <S : Any> Skill.Builder<S>.requireLearnedCondition(crossinline block: SkillContext<*>.() -> RegistryEntry<out Skill<*>>): SkillCondition =
    requireLearnedSkillCondition(block).also { condition = it }

fun <S : Any> Skill.Builder<S>.requireLearnedCondition(skill: RegistryEntry<out Skill<*>>): SkillCondition =
    requireLearnedSkillCondition(skill).also { condition = it }

fun <S : Any> Skill.Builder<S>.requireLearnedCondition(): SkillCondition =
    requireLearnedSkillCondition().also { condition = it }

inline fun <S : Any> Skill.Builder<S>.requireNoCooldownCondition(crossinline block: SkillContext<*>.() -> RegistryEntry<out Skill<*>>): SkillCondition =
    requireNoCooldownSkillCondition(block).also { condition = it }

fun <S : Any> Skill.Builder<S>.requireNoCooldownCondition(skill: RegistryEntry<out Skill<*>>): SkillCondition =
    requireNoCooldownSkillCondition(skill).also { condition = it }

fun <S : Any> Skill.Builder<S>.requireNoCooldownCondition(): SkillCondition =
    requireNoCooldownSkillCondition().also { condition = it }

fun <S : Any> Skill.Builder<S>.requireInGameCondition(): SkillCondition =
    requireInGameSkillCondition().also { condition = it }

fun <S : Any> Skill.Builder<S>.defaultCondition(): SkillCondition = defaultSkillCondition().also { condition = it }

inline fun <S : Any> Skill.Builder<S>.durationProvider(crossinline block: SkillContext<*>.() -> Int): SkillDurationProvider =
    skillDurationProvider(block).also { durationProvider = it }

fun <S : Any> Skill.Builder<S>.constantDurationProvider(duration: Int): SkillDurationProvider =
    constantSkillDurationProvider(duration).also { durationProvider = it }

inline fun <S : Any> Skill.Builder<S>.cancelHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelHandler<S> =
    skillCancelHandler(block).also { cancelHandler = it }

fun <S : Any> Skill.Builder<S>.alwaysAllowCancelHandler(): SkillCancelHandler<S> =
    alwaysAllowSkillCancelHandler<S>().also { cancelHandler = it }

fun <S : Any> Skill.Builder<S>.alwaysDenyCancelHandler(): SkillCancelHandler<S> =
    alwaysDenySkillCancelHandler<S>().also { cancelHandler = it }

inline fun <S : Any> Skill.Builder<S>.interruptHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptHandler<S> =
    skillInterruptHandler(block).also { interruptHandler = it }

fun <S : Any> Skill.Builder<S>.alwaysAllowInterruptHandler(): SkillInterruptHandler<S> =
    alwaysAllowSkillInterruptHandler<S>().also { interruptHandler = it }

fun <S : Any> Skill.Builder<S>.alwaysDenyInterruptHandler(): SkillInterruptHandler<S> =
    alwaysDenySkillInterruptHandler<S>().also { interruptHandler = it }

inline fun <S : Any> Skill.Builder<S>.stateFactory(crossinline block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    skillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.alwaysOkStateFactory(crossinline block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    alwaysOkSkillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.alwaysErrorStateFactory(crossinline block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    alwaysErrorSkillStateFactory<S>(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.constantStateFactory(state: S): SkillStateFactory<S> =
    constantSkillStateFactory(state).also { stateFactory = it }