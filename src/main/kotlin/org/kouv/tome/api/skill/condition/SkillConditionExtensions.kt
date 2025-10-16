package org.kouv.tome.api.skill.condition

import net.minecraft.registry.entry.RegistryEntry
import org.kouv.tome.api.skill.Skill
import org.kouv.tome.api.skill.SkillContext
import org.kouv.tome.api.skill.SkillResponse

inline fun skillCondition(crossinline block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    SkillCondition { context -> context.block() }

inline fun requireLearnedSkillCondition(crossinline block: SkillContext<*>.() -> RegistryEntry<out Skill<*>>): SkillCondition =
    SkillCondition.requireLearned { context -> context.block() }

fun requireLearnedSkillCondition(skill: RegistryEntry<out Skill<*>>): SkillCondition =
    SkillCondition.requireLearned(skill)

fun requireLearnedSkillCondition(): SkillCondition = SkillCondition.requireLearned()

inline fun requireNoCooldownSkillCondition(crossinline block: SkillContext<*>.() -> RegistryEntry<out Skill<*>>): SkillCondition =
    SkillCondition.requireNoCooldown { context -> context.block() }

fun requireNoCooldownSkillCondition(skill: RegistryEntry<out Skill<*>>): SkillCondition =
    SkillCondition.requireNoCooldown(skill)

fun requireNoCooldownSkillCondition(): SkillCondition = SkillCondition.requireNoCooldown()

fun requireInGameSkillCondition(): SkillCondition = SkillCondition.requireInGame()

fun defaultSkillCondition(): SkillCondition = SkillCondition.defaultConditions()