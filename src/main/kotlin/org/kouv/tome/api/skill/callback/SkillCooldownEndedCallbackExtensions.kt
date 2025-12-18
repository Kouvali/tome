package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun SkillCooldownEndedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownEndedCallback =
    SkillCooldownEndedCallback { context -> context.block() }

@Suppress("FunctionName")
fun NoOpSkillCooldownEndedCallback(): SkillCooldownEndedCallback = SkillCooldownEndedCallback.noOp()

operator fun SkillCooldownEndedCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownEndedCallback =
    andThen(block)