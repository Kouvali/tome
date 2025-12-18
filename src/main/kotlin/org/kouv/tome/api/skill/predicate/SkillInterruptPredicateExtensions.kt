package org.kouv.tome.api.skill.predicate

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillInterruptPredicate(block: SkillInstance<out S>.() -> Boolean): SkillInterruptPredicate<S> =
    SkillInterruptPredicate { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> AllowedSkillInterruptPredicate(): SkillInterruptPredicate<S> = SkillInterruptPredicate.allowed()

@Suppress("FunctionName")
fun <S : Any> DeniedSkillInterruptPredicate(): SkillInterruptPredicate<S> = SkillInterruptPredicate.denied()