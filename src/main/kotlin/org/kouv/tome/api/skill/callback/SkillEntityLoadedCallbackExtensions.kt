package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillEntityLoadedCallback(block: SkillContext<*>.() -> Unit): SkillEntityLoadedCallback =
    SkillEntityLoadedCallback(block)

fun noOpSkillEntityLoadedCallback(): SkillEntityLoadedCallback = SkillEntityLoadedCallback.noOp()

operator fun SkillEntityLoadedCallback.plus(block: SkillContext<*>.() -> Unit): SkillEntityLoadedCallback =
    andThen(block)