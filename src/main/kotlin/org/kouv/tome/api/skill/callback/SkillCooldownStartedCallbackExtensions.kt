package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun SkillCooldownStartedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownStartedCallback =
    SkillCooldownStartedCallback { context -> context.block() }

@Suppress("FunctionName")
fun NoOpSkillCooldownStartedCallback(): SkillCooldownStartedCallback = SkillCooldownStartedCallback.noOp()

operator fun SkillCooldownStartedCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownStartedCallback =
    andThen(block)