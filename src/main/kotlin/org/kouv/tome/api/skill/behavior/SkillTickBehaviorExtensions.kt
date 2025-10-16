package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillTickBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    SkillTickBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillTickBehavior(): SkillTickBehavior<S> = SkillTickBehavior.noOp()