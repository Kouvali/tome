package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillEndBehavior(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    SkillEndBehavior(block)

fun <S : Any> noOpSkillEndBehavior(): SkillEndBehavior<S> = SkillEndBehavior.noOp()

operator fun <S : Any> SkillEndBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    andThen(block)