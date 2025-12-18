package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun SkillRemovedCallback(block: SkillContext<*>.() -> Unit): SkillRemovedCallback =
    SkillRemovedCallback { context -> context.block() }

@Suppress("FunctionName")
fun NoOpSkillRemovedCallback(): SkillRemovedCallback = SkillRemovedCallback.noOp()

operator fun SkillRemovedCallback.plus(block: SkillContext<*>.() -> Unit): SkillRemovedCallback =
    andThen(block)