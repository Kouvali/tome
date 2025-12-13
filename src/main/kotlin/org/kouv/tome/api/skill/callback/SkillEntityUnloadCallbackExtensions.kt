package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

inline fun skillEntityUnloadCallback(crossinline block: SkillContext<*>.() -> Unit): SkillEntityUnloadCallback =
    SkillEntityUnloadCallback { instance -> instance.block() }

fun noOpSkillEntityUnloadCallback(): SkillEntityUnloadCallback = SkillEntityUnloadCallback.noOp()