package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillRemovedCallback(block: SkillContext<*>.() -> Unit): SkillRemovedCallback =
    SkillRemovedCallback(block)

fun noOpSkillRemovedCallback(): SkillRemovedCallback = SkillRemovedCallback.noOp()

operator fun SkillRemovedCallback.plus(block: SkillContext<*>.() -> Unit): SkillRemovedCallback =
    andThen(block)