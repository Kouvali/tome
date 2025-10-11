package org.kouv.tome.api.skill.duration

import org.kouv.tome.api.skill.SkillContext

inline fun skillDurationProvider(crossinline block: SkillContext<*>.() -> Int): SkillDurationProvider =
    SkillDurationProvider { it.block() }