package org.kouv.tome.api.skill.state

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

fun <S : Any> SkillStateFactory(block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    SkillStateFactory { context -> context.block() }

@Suppress("FunctionName")
fun <S : Any> AlwaysOkSkillStateFactory(block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    SkillStateFactory.alwaysOk(block)

@Suppress("FunctionName")
fun <S : Any> AlwaysErrorSkillStateFactory(block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    SkillStateFactory.alwaysError(block)

@Suppress("FunctionName")
fun <S : Any> ConstantSkillStateFactory(state: S): SkillStateFactory<S> =
    SkillStateFactory.constant(state)