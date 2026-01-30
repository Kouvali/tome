package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillCancelBehavior(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    SkillCancelBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillCancelBehavior(): SkillCancelBehavior<S> = SkillCancelBehavior.noOp()

operator fun <S : Any> SkillCancelBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    andThen(block)