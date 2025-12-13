package org.kouv.tome.api.skill.predicate

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillInterruptPredicate(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptPredicate<S> =
    SkillInterruptPredicate { instance -> instance.block() }

fun <S : Any> allowedSkillInterruptPredicate(): SkillInterruptPredicate<S> = SkillInterruptPredicate.allowed()

fun <S : Any> deniedSkillInterruptPredicate(): SkillInterruptPredicate<S> = SkillInterruptPredicate.denied()