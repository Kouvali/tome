package org.kouv.tome.api.skill.state

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

inline fun <S : Any> skillStateFactory(crossinline block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    SkillStateFactory { context -> context.block() }

inline fun <S : Any> alwaysOkSkillStateFactory(crossinline block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    SkillStateFactory.alwaysOk { context -> context.block() }

inline fun <S : Any> alwaysErrorSkillStateFactory(crossinline block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    SkillStateFactory.alwaysError { context -> context.block() }

fun <S : Any> constantSkillStateFactory(state: S): SkillStateFactory<S> = SkillStateFactory.constant(state)