package org.kouv.tome.api.skill.predicate

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillCancelPredicate(block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    SkillCancelPredicate(block)

fun <S : Any> allowedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.allowed()

fun <S : Any> deniedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.denied()