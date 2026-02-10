package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

fun skillRemoveBehavior(block: SkillContext<*>.() -> Unit): SkillRemoveBehavior =
    SkillRemoveBehavior { context -> context.block() }

fun noOpSkillRemoveBehavior(): SkillRemoveBehavior = SkillRemoveBehavior.noOp()

operator fun SkillRemoveBehavior.plus(block: SkillContext<*>.() -> Unit): SkillRemoveBehavior =
    andThen(block)