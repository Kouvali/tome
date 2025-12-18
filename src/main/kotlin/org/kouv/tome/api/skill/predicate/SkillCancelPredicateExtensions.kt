package org.kouv.tome.api.skill.predicate

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillCancelPredicate(block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    SkillCancelPredicate { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> AllowedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.allowed()

@Suppress("FunctionName")
fun <S : Any> DeniedSkillCancelPredicate(): SkillCancelPredicate<S> = SkillCancelPredicate.denied()