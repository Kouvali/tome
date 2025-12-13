package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillAddCallback(block: SkillContext<*>.() -> Unit): SkillAddCallback =
    SkillAddCallback(block)

fun noOpSkillAddCallback(): SkillAddCallback = SkillAddCallback.noOp()

operator fun SkillAddCallback.plus(block: SkillContext<*>.() -> Unit): SkillAddCallback =
    andThen(block)