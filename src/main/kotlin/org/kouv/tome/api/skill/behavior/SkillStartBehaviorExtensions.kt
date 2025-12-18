package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> SkillStartBehavior(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    SkillStartBehavior { instance -> instance.block() }

@Suppress("FunctionName")
fun <S : Any> NoOpSkillStartBehavior(): SkillStartBehavior<S> = SkillStartBehavior.noOp()

operator fun <S : Any> SkillStartBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    andThen(block)