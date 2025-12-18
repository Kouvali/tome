package org.kouv.tome.api.skill.condition

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

fun SkillCondition(block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    SkillCondition { context -> context.block() }

@Suppress("FunctionName")
fun RequireLearnedSkillCondition(): SkillCondition = SkillCondition.requireLearned()

@Suppress("FunctionName")
fun RequireNoCooldownSkillCondition(): SkillCondition = SkillCondition.requireNoCooldown()

@Suppress("FunctionName")
fun RequireInGameSkillCondition(): SkillCondition = SkillCondition.requireInGame()

@Suppress("FunctionName")
fun DefaultSkillCondition(): SkillCondition = SkillCondition.defaultConditions()