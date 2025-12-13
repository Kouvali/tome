package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

inline fun skillCooldownStartBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillCooldownStartBehavior =
    SkillCooldownStartBehavior { instance -> instance.block() }

fun noOpSkillCooldownStartBehavior(): SkillCooldownStartBehavior = SkillCooldownStartBehavior.noOp()