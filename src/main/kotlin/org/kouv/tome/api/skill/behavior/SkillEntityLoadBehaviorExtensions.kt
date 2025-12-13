package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

inline fun skillEntityLoadBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillEntityLoadBehavior =
    SkillEntityLoadBehavior { instance -> instance.block() }

fun noOpSkillEntityLoadBehavior(): SkillEntityLoadBehavior = SkillEntityLoadBehavior.noOp()