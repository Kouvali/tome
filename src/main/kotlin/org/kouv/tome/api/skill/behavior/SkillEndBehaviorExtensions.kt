package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillEndBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    SkillEndBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillEndBehavior(): SkillEndBehavior<S> = SkillEndBehavior.noOp()