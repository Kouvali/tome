package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun SkillAddedCallback(block: SkillContext<*>.() -> Unit): SkillAddedCallback =
    SkillAddedCallback { context -> context.block() }

@Suppress("FunctionName")
fun NoOpSkillAddedCallback(): SkillAddedCallback = SkillAddedCallback.noOp()

operator fun SkillAddedCallback.plus(block: SkillContext<*>.() -> Unit): SkillAddedCallback =
    andThen(block)