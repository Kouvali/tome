package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillEntityUnloadingCallback(block: SkillContext<*>.() -> Unit): SkillEntityUnloadingCallback =
    SkillEntityUnloadingCallback(block)

fun noOpSkillEntityUnloadingCallback(): SkillEntityUnloadingCallback = SkillEntityUnloadingCallback.noOp()

operator fun SkillEntityUnloadingCallback.plus(block: SkillContext<*>.() -> Unit): SkillEntityUnloadingCallback =
    andThen(block)