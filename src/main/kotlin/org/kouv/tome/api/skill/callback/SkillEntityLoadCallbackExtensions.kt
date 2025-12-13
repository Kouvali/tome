package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

inline fun skillEntityLoadCallback(crossinline block: SkillContext<*>.() -> Unit): SkillEntityLoadCallback =
    SkillEntityLoadCallback { instance -> instance.block() }

fun noOpSkillEntityLoadCallback(): SkillEntityLoadCallback = SkillEntityLoadCallback.noOp()