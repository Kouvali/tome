package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

fun <S : Any> skillStartBehavior(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    SkillStartBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillStartBehavior(): SkillStartBehavior<S> = SkillStartBehavior.noOp()

operator fun <S : Any> SkillStartBehavior<S>.plus(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    andThen(block)