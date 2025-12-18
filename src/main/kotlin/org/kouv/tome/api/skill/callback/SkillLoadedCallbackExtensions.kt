package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun SkillLoadedCallback(block: SkillContext<*>.() -> Unit): SkillLoadedCallback =
    SkillLoadedCallback { context -> context.block() }

@Suppress("FunctionName")
fun NoOpSkillLoadedCallback(): SkillLoadedCallback = SkillLoadedCallback.noOp()

operator fun SkillLoadedCallback.plus(block: SkillContext<*>.() -> Unit): SkillLoadedCallback =
    andThen(block)