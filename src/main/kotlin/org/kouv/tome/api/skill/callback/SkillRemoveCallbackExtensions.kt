package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillRemoveCallback(block: SkillContext<*>.() -> Unit): SkillRemoveCallback =
    SkillRemoveCallback(block)

fun noOpSkillRemoveCallback(): SkillRemoveCallback = SkillRemoveCallback.noOp()

operator fun SkillRemoveCallback.plus(block: SkillContext<*>.() -> Unit): SkillRemoveCallback =
    andThen(block)