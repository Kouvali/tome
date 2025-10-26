package org.kouv.tome.api.skill.action

import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

inline fun skillAction(crossinline block: SkillContext.() -> SkillResponse): SkillAction =
    SkillAction { context -> context.block() }