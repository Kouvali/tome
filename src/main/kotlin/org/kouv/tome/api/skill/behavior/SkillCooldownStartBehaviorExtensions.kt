package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

fun skillCooldownStartBehavior(block: SkillContext<*>.() -> Unit): SkillCooldownStartBehavior =
    SkillCooldownStartBehavior { context -> context.block() }

fun noOpSkillCooldownStartBehavior(): SkillCooldownStartBehavior = SkillCooldownStartBehavior.noOp()

operator fun SkillCooldownStartBehavior.plus(block: SkillContext<*>.() -> Unit): SkillCooldownStartBehavior =
    andThen(block)