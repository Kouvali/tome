package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillCancelBehavior(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    SkillCancelBehavior(block)

fun <S : Any> noOpSkillCancelBehavior(): SkillCancelBehavior<S> = SkillCancelBehavior.noOp()

operator fun <S : Any> SkillCancelBehavior<S>.plus(other: SkillCancelBehavior<S>): SkillCancelBehavior<S> =
    andThen(other)