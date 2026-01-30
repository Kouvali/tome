package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillTickBehavior(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    SkillTickBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillTickBehavior(): SkillTickBehavior<S> = SkillTickBehavior.noOp()

operator fun <S : Any> SkillTickBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    andThen(block)