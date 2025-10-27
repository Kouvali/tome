package org.kouv.tome.api.skill.condition

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillCancelCondition(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelCondition<S> =
    SkillCancelCondition { instance -> instance.block() }

fun <S : Any> allowedSkillCancelCondition(): SkillCancelCondition<S> = SkillCancelCondition.allowed()

fun <S : Any> deniedSkillCancelCondition(): SkillCancelCondition<S> = SkillCancelCondition.denied()