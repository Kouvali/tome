package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillCompleteBehavior(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    SkillCompleteBehavior { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> NoOpSkillCompleteBehavior(): SkillCompleteBehavior<S> = SkillCompleteBehavior.noOp()

operator fun <S : Any> SkillCompleteBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    andThen(block)