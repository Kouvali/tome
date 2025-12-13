package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillEntityUnloadCallback(block: SkillContext<*>.() -> Unit): SkillEntityUnloadCallback =
    SkillEntityUnloadCallback(block)

fun noOpSkillEntityUnloadCallback(): SkillEntityUnloadCallback = SkillEntityUnloadCallback.noOp()

operator fun SkillEntityUnloadCallback.plus(block: SkillContext<*>.() -> Unit): SkillEntityUnloadCallback =
    andThen(block)