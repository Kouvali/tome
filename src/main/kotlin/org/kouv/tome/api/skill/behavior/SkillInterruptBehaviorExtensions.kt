package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillInterruptBehavior(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    SkillInterruptBehavior { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> NoOpSkillInterruptBehavior(): SkillInterruptBehavior<S> = SkillInterruptBehavior.noOp()

operator fun <S : Any> SkillInterruptBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    andThen(block)