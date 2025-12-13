package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillContext

inline fun skillRemoveBehavior(crossinline block: SkillContext<*>.() -> Unit): SkillRemoveBehavior =
    SkillRemoveBehavior { instance -> instance.block() }

fun noOpSkillRemoveBehavior(): SkillRemoveBehavior = SkillRemoveBehavior.noOp()