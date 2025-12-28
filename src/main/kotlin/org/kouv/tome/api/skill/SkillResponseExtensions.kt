package org.kouv.tome.api.skill

import net.minecraft.network.chat.Component

fun Success(): SkillResponse.Success = SkillResponse.success()

fun Failure(reason: Component): SkillResponse.Failure = SkillResponse.failure(reason)

@Suppress("FunctionName")
fun InProgress(): SkillResponse.Failure = SkillResponse.inProgress()

@Suppress("FunctionName")
fun NotLearned(): SkillResponse.Failure = SkillResponse.notLearned()

@Suppress("FunctionName")
fun Cooldown(): SkillResponse.Failure = SkillResponse.cooldown()

@Suppress("FunctionName")
fun Unavailable(): SkillResponse.Failure = SkillResponse.unavailable()