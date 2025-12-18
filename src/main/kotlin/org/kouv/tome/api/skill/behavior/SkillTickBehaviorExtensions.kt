package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillTickBehavior(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    SkillTickBehavior { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> NoOpSkillTickBehavior(): SkillTickBehavior<S> = SkillTickBehavior.noOp()

operator fun <S : Any> SkillTickBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    andThen(block)