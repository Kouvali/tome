package org.kouv.tome.api.skill.duration

import org.kouv.tome.api.skill.SkillContext

fun skillDurationProvider(block: SkillContext<*>.() -> Int): SkillDurationProvider =
    SkillDurationProvider { context -> context.block() }

fun constantSkillDurationProvider(duration: Int): SkillDurationProvider = SkillDurationProvider.constant(duration)

fun infiniteSkillDurationProvider(): SkillDurationProvider = SkillDurationProvider.infinite()

fun instantSkillDurationProvider(): SkillDurationProvider = SkillDurationProvider.instant()