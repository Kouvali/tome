package org.kouv.tome.api.skill

import org.kouv.tome.api.skill.action.SkillAction
import org.kouv.tome.api.skill.action.alwaysFailureSkillAction
import org.kouv.tome.api.skill.action.alwaysSuccessSkillAction
import org.kouv.tome.api.skill.action.skillAction
import org.kouv.tome.api.skill.condition.*

inline fun skill(builderAction: Skill.Builder.() -> Unit): Skill = Skill.builder().apply(builderAction).build()

inline fun Skill.Builder.action(crossinline block: SkillContext.() -> SkillResponse): SkillAction =
    skillAction(block).also { action = it }

inline fun Skill.Builder.alwaysSuccessAction(crossinline block: SkillContext.() -> Unit): SkillAction =
    alwaysSuccessSkillAction(block).also { action = it }

fun Skill.Builder.alwaysSuccessAction(): SkillAction = alwaysSuccessSkillAction().also { action = it }

inline fun Skill.Builder.alwaysFailureAction(
    failure: SkillResponse.Failure,
    crossinline block: SkillContext.() -> Unit
): SkillAction = alwaysFailureSkillAction(failure, block).also { action = it }

fun Skill.Builder.alwaysFailureAction(failure: SkillResponse.Failure): SkillAction =
    alwaysFailureSkillAction(failure).also { action = it }

inline fun Skill.Builder.condition(crossinline block: SkillContext.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }

fun Skill.Builder.requireNotChannelingCondition(): SkillCondition =
    requireNotChannelingSkillCondition().also { condition = it }

fun Skill.Builder.requireLearnedCondition(): SkillCondition = requireLearnedSkillCondition().also { condition = it }

fun Skill.Builder.requireNoCooldownCondition(): SkillCondition =
    requireNoCooldownSkillCondition().also { condition = it }

fun Skill.Builder.requireInGameCondition(): SkillCondition = requireInGameSkillCondition().also { condition = it }

fun Skill.Builder.defaultCondition(): SkillCondition = defaultSkillCondition().also { condition = it }