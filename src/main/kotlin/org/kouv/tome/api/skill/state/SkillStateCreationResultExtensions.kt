package org.kouv.tome.api.skill.state

import org.kouv.tome.api.skill.SkillResponse

fun <S> Ok(state: S): SkillStateCreationResult.Ok<S> =
    SkillStateCreationResult.ok(state)

fun <S> Error(failure: SkillResponse.Failure): SkillStateCreationResult.Error<S> =
    SkillStateCreationResult.error(failure)