package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillCooldownEndCallback(block: SkillContext<*>.() -> Unit): SkillCooldownEndCallback =
    SkillCooldownEndCallback(block)

fun noOpSkillCooldownEndCallback(): SkillCooldownEndCallback = SkillCooldownEndCallback.noOp()

operator fun SkillCooldownEndCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownEndCallback =
    andThen(block)