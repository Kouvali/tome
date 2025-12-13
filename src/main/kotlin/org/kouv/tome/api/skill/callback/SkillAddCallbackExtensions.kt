package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

inline fun skillAddCallback(crossinline block: SkillContext<*>.() -> Unit): SkillAddCallback =
    SkillAddCallback { instance -> instance.block() }

fun noOpSkillAddCallback(): SkillAddCallback = SkillAddCallback.noOp()

operator fun SkillAddCallback.plus(block: SkillContext<*>.() -> Unit): SkillAddCallback = andThen(block)