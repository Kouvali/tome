package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillStartBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    SkillStartBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillStartBehavior(): SkillStartBehavior<S> = SkillStartBehavior.noOp()