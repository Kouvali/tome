package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillInterruptBehavior(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    SkillInterruptBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillInterruptBehavior(): SkillInterruptBehavior<S> = SkillInterruptBehavior.noOp()

operator fun <S : Any> SkillInterruptBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    andThen(block)