package org.kouv.tome.api.skill.action

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

inline fun skillAction(crossinline block: SkillContext.() -> SkillResponse): SkillAction =
    SkillAction { context -> context.block() }

inline fun alwaysSuccessSkillAction(crossinline block: SkillContext.() -> Unit): SkillAction =
    SkillAction.alwaysSuccess { context -> context.block() }

fun alwaysSuccessSkillAction(): SkillAction = SkillAction.alwaysSuccess()

inline fun alwaysFailureSkillAction(
    failure: SkillResponse.Failure,
    crossinline block: SkillContext.() -> Unit
): SkillAction = SkillAction.alwaysFailure(failure) { context -> context.block() }

fun alwaysFailureSkillAction(failure: SkillResponse.Failure): SkillAction = SkillAction.alwaysFailure(failure)