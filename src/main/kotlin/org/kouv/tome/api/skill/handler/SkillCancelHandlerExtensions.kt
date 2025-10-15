package org.kouv.tome.api.skill.handler

import org.kouv.tome.api.skill.SkillInstance

inline fun <S : Any> skillCancelHandler(crossinline block: SkillInstance<out S>.() -> Boolean): SkillCancelHandler<S> =
    SkillCancelHandler { instance -> instance.block() }

fun <S : Any> alwaysAllowSkillCancelHandler(): SkillCancelHandler<S> = SkillCancelHandler.alwaysAllow()

fun <S : Any> alwaysDenySkillCancelHandler(): SkillCancelHandler<S> = SkillCancelHandler.alwaysDeny()