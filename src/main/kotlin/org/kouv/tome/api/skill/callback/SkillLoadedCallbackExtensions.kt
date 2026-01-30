package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillLoadedCallback(block: SkillContext<*>.() -> Unit): SkillLoadedCallback =
    SkillLoadedCallback { context -> context.block() }

fun noOpSkillLoadedCallback(): SkillLoadedCallback = SkillLoadedCallback.noOp()

operator fun SkillLoadedCallback.plus(block: SkillContext<*>.() -> Unit): SkillLoadedCallback =
    andThen(block)