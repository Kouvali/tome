package org.kouv.tome.api.skill.behavior

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillCompleteBehavior(crossinline block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    SkillCompleteBehavior { instance -> instance.block() }

fun <S : Any> noOpSkillCompleteBehavior(): SkillCompleteBehavior<S> = SkillCompleteBehavior.noOp()