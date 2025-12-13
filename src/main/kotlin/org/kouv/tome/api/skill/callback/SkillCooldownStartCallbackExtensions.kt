package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillCooldownStartCallback(block: SkillContext<*>.() -> Unit): SkillCooldownStartCallback =
    SkillCooldownStartCallback(block)

fun noOpSkillCooldownStartCallback(): SkillCooldownStartCallback = SkillCooldownStartCallback.noOp()

operator fun SkillCooldownStartCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownStartCallback =
    andThen(block)