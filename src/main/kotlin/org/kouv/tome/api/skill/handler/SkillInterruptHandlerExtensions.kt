package org.kouv.tome.api.skill.handler

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillInterruptHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillInterruptHandler<S> =
    SkillInterruptHandler { it.block() }