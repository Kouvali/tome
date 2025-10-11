package org.kouv.tome.api.skill

import org.kouv.tome.api.skill.behavior.*
import org.kouv.tome.api.skill.condition.SkillCondition
import org.kouv.tome.api.skill.condition.skillCondition
import org.kouv.tome.api.skill.duration.SkillDurationProvider
import org.kouv.tome.api.skill.duration.skillDurationProvider
import org.kouv.tome.api.skill.handler.SkillCancelHandler
import org.kouv.tome.api.skill.handler.SkillInterruptHandler
import org.kouv.tome.api.skill.handler.skillCancelHandler
import org.kouv.tome.api.skill.handler.skillInterruptHandler
import org.kouv.tome.api.skill.state.SkillStateCreationResult
import org.kouv.tome.api.skill.state.SkillStateFactory
import org.kouv.tome.api.skill.state.skillStateFactory

inline fun <S : Any> skill(builderAction: Skill.Builder<S>.() -> Unit): Skill<S> =
    Skill.builder<S>().apply(builderAction).build()

inline fun Skill.Builder<*>.condition(crossinline block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }

inline fun Skill.Builder<*>.durationProvider(crossinline block: SkillContext<*>.() -> Int): SkillDurationProvider =
    skillDurationProvider(block).also { durationProvider = it }

inline fun <S : Any> Skill.Builder<S>.stateFactory(crossinline block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    skillStateFactory(block).also { stateFactory = it }

inline fun <S : Any> Skill.Builder<S>.startBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    skillStartBehavior(block).also { startBehavior = it }

inline fun <S : Any> Skill.Builder<S>.tickBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    skillTickBehavior(block).also { tickBehavior = it }

inline fun <S : Any> Skill.Builder<S>.completeBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    skillCompleteBehavior(block).also { completeBehavior = it }

inline fun <S : Any> Skill.Builder<S>.endBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    skillEndBehavior(block).also { endBehavior = it }

inline fun <S : Any> Skill.Builder<S>.cancelHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelHandler<S> =
    skillCancelHandler(block).also { cancelHandler = it }

inline fun <S : Any> Skill.Builder<S>.interruptHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptHandler<S> =
    skillInterruptHandler(block).also { interruptHandler = it }