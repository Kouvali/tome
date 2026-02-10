package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

fun skillAddBehavior(block: SkillContext<*>.() -> Unit): SkillAddBehavior =
    SkillAddBehavior { context -> context.block() }

fun noOpSkillAddBehavior(): SkillAddBehavior = SkillAddBehavior.noOp()

operator fun SkillAddBehavior.plus(block: SkillContext<*>.() -> Unit): SkillAddBehavior =
    andThen(block)