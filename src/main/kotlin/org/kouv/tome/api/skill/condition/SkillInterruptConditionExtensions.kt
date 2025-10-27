package org.kouv.tome.api.skill.condition

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillInterruptCondition(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptCondition<S> =
    SkillInterruptCondition { instance -> instance.block() }

fun <S : Any> allowedSkillInterruptCondition(): SkillInterruptCondition<S> = SkillInterruptCondition.allowed()

fun <S : Any> deniedSkillInterruptCondition(): SkillInterruptCondition<S> = SkillInterruptCondition.denied()