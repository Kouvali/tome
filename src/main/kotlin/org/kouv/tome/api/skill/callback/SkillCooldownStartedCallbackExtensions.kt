package org.kouv.tome.api.skill.callback

import org.kouv.tome.api.skill.SkillContext

fun skillCooldownStartedCallback(block: SkillContext<*>.() -> Unit): SkillCooldownStartedCallback =
    SkillCooldownStartedCallback(block)

fun noOpSkillCooldownStartedCallback(): SkillCooldownStartedCallback = SkillCooldownStartedCallback.noOp()

operator fun SkillCooldownStartedCallback.plus(block: SkillContext<*>.() -> Unit): SkillCooldownStartedCallback =
    andThen(block)