package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillInterruptBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    SkillInterruptBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillInterruptBehavior(): SkillInterruptBehavior<S> = SkillInterruptBehavior.noOp()