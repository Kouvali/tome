package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillEndBehavior(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    SkillEndBehavior { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> NoOpSkillEndBehavior(): SkillEndBehavior<S> = SkillEndBehavior.noOp()

operator fun <S : Any> SkillEndBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    andThen(block)