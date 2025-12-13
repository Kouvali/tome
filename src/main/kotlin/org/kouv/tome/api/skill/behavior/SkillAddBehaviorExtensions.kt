package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

inline fun skillAddBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillAddBehavior =
    SkillAddBehavior { instance -> instance.block() }

fun noOpSkillAddBehavior(): SkillAddBehavior = SkillAddBehavior.noOp()