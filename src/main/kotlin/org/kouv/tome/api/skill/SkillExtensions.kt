package org.kouv.tome.api.skill

import org.kouv.tome.api.skill.action.SkillAction
import org.kouv.tome.api.skill.action.skillAction
import org.kouv.tome.api.skill.condition.SkillCondition
import org.kouv.tome.api.skill.condition.skillCondition

inline fun skill(builderAction: Skill.Builder.() -> Unit): Skill =
    Skill.builder().apply(builderAction).build()

inline fun Skill.Builder.action(crossinline block: SkillContext.() -> SkillResponse): SkillAction =
    skillAction(block).also { action = it }

inline fun Skill.Builder.condition(crossinline block: SkillContext.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }