package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillAddedCallback(block: SkillContext<*>.() -> Unit): SkillAddedCallback =
    SkillAddedCallback { context -> context.block() }

fun noOpSkillAddedCallback(): SkillAddedCallback = SkillAddedCallback.noOp()

operator fun SkillAddedCallback.plus(block: SkillContext<*>.() -> Unit): SkillAddedCallback =
    andThen(block)