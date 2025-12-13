package org.kouv.tome.api.skill.predicate

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillCancelPredicate(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    SkillCancelPredicate { instance -> instance.block() }

fun <S : Any> allowedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.allowed()

fun <S : Any> deniedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.denied()