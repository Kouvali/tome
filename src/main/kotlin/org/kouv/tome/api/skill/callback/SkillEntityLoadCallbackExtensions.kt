package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillEntityLoadCallback(block: SkillContext<*>.() -> Unit): SkillEntityLoadCallback =
    SkillEntityLoadCallback(block)

fun noOpSkillEntityLoadCallback(): SkillEntityLoadCallback = SkillEntityLoadCallback.noOp()

operator fun SkillEntityLoadCallback.plus(block: SkillContext<*>.() -> Unit): SkillEntityLoadCallback =
    andThen(block)