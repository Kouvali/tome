package org.kouv.tome.api.skill.condition

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

inline fun skillCondition(crossinline block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    SkillCondition { context -> context.block() }

fun requireLearnedSkillCondition(): SkillCondition = SkillCondition.requireLearned()

fun requireNoCooldownSkillCondition(): SkillCondition = SkillCondition.requireNoCooldown()

fun requireInGameSkillCondition(): SkillCondition = SkillCondition.requireInGame()

fun defaultSkillCondition(): SkillCondition = SkillCondition.defaultConditions()