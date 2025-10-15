package org.kouv.tome.api.skill.duration

import org.kouv.tome.api.skill.SkillContext

inline fun skillDurationProvider(crossinline block: SkillContext<*>.() -> Int): SkillDurationProvider =
    SkillDurationProvider { context -> context.block() }

fun constantSkillDurationProvider(duration: Int): SkillDurationProvider = SkillDurationProvider.constant(duration)