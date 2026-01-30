package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillCooldownEndedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownEndedCallback =
    SkillCooldownEndedCallback { context -> context.block() }

fun noOpSkillCooldownEndedCallback(): SkillCooldownEndedCallback = SkillCooldownEndedCallback.noOp()

operator fun SkillCooldownEndedCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownEndedCallback =
    andThen(block)