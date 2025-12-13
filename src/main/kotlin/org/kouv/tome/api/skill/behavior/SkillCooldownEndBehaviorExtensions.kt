package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

inline fun skillCooldownEndBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownEndBehavior =
    SkillCooldownEndBehavior { instance -> instance.block() }

fun noOpSkillCooldownEndBehavior(): SkillCooldownEndBehavior = SkillCooldownEndBehavior.noOp()