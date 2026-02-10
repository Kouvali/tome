package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

fun skillCooldownEndBehavior(block: SkillContext<*>.() -> Unit): SkillCooldownEndBehavior =
    SkillCooldownEndBehavior { context -> context.block() }

fun noOpSkillCooldownEndBehavior(): SkillCooldownEndBehavior = SkillCooldownEndBehavior.noOp()

operator fun SkillCooldownEndBehavior.plus(block: SkillContext<*>.() -> Unit): SkillCooldownEndBehavior =
    andThen(block)