package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

inline fun skillCooldownEndCallback(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownEndCallback =
    SkillCooldownEndCallback { instance -> instance.block() }

fun noOpSkillCooldownEndCallback(): SkillCooldownEndCallback = SkillCooldownEndCallback.noOp()

operator fun SkillCooldownEndCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownEndCallback = andThen(block)