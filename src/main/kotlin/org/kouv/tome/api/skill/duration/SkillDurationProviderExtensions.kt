package org.kouv.tome.api.skill.duration

import org.kouv.tome.api.skill.SkillContext

fun SkillDurationProvider(block: SkillContext<*>.() -> Int): SkillDurationProvider =
    SkillDurationProvider { context -> context.block() }

@Suppress("FunctionName")
fun ConstantSkillDurationProvider(duration: Int): SkillDurationProvider = SkillDurationProvider.constant(duration)

@Suppress("FunctionName")
fun InfiniteSkillDurationProvider(): SkillDurationProvider = SkillDurationProvider.infinite()

@Suppress("FunctionName")
fun InstantSkillDurationProvider(): SkillDurationProvider = SkillDurationProvider.instant()