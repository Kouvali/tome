package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillCancelBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    SkillCancelBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillCancelBehavior(): SkillCancelBehavior<S> = SkillCancelBehavior.noOp()