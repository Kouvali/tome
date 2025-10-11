package org.kouv.tome.api.skill.state

import org.kouv.tome.api.skill.SkillContext

inline fun <S : Any> skillStateFactory(crossinline block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    SkillStateFactory { it.block() }