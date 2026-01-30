package org.kouv.tome.api.skill.state

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

fun <S : Any> skillStateFactory(block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    SkillStateFactory { context -> context.block() }

fun <S : Any> alwaysOkSkillStateFactory(block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    SkillStateFactory.alwaysOk(block)

fun <S : Any> alwaysErrorSkillStateFactory(block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    SkillStateFactory.alwaysError(block)

fun <S : Any> constantSkillStateFactory(state: S): SkillStateFactory<S> =
    SkillStateFactory.constant(state)