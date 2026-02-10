package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

fun skillLoadBehavior(block: SkillContext<*>.() -> Unit): SkillLoadBehavior =
    SkillLoadBehavior { context -> context.block() }

fun noOpSkillLoadBehavior(): SkillLoadBehavior = SkillLoadBehavior.noOp()

operator fun SkillLoadBehavior.plus(block: SkillContext<*>.() -> Unit): SkillLoadBehavior =
    andThen(block)