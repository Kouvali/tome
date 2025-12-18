package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillCancelBehavior(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    SkillCancelBehavior { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> NoOpSkillCancelBehavior(): SkillCancelBehavior<S> = SkillCancelBehavior.noOp()

operator fun <S : Any> SkillCancelBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    andThen(block)