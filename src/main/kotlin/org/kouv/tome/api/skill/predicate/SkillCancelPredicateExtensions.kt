package org.kouv.tome.api.skill.predicate

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillCancelPredicate(block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    SkillCancelPredicate { instance -> instance.block() }

fun <S : Any> allowedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.allowed()

fun <S : Any> deniedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.denied()

operator fun <S : Any> SkillCancelPredicate<S>.plus(other: SkillCancelPredicate<S>): SkillCancelPredicate<S> =
    and(other)