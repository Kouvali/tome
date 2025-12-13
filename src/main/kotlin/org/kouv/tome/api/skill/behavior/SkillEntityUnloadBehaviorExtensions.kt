package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

inline fun skillEntityUnloadBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillEntityUnloadBehavior =
    SkillEntityUnloadBehavior { instance -> instance.block() }

fun noOpSkillEntityUnloadBehavior(): SkillEntityUnloadBehavior = SkillEntityUnloadBehavior.noOp()