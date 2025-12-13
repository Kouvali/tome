package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

inline fun skillCooldownStartCallback(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownStartCallback =
    SkillCooldownStartCallback { instance -> instance.block() }

fun noOpSkillCooldownStartCallback(): SkillCooldownStartCallback = SkillCooldownStartCallback.noOp()