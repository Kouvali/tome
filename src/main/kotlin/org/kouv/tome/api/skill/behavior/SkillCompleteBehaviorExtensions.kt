package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillCompleteBehavior(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    SkillCompleteBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillCompleteBehavior(): SkillCompleteBehavior<S> = SkillCompleteBehavior.noOp()

operator fun <S : Any> SkillCompleteBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    andThen(block)