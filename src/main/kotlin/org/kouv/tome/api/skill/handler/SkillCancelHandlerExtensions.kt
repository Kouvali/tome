package org.kouv.tome.api.skill.handler

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillCancelHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelHandler<S> =
    SkillCancelHandler { it.block() }