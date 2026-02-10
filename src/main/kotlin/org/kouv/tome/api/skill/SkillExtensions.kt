package org.kouv.tome.api.skill

import org.kouv.tome.api.entity.attribute.AttributeModifierSet
import org.kouv.tome.api.entity.attribute.attributeModifierSet
import org.kouv.tome.api.skill.behavior.*
import org.kouv.tome.api.skill.condition.*
import org.kouv.tome.api.skill.duration.*
import org.kouv.tome.api.skill.predicate.*
import org.kouv.tome.api.skill.state.*

fun <S : Any> skill(builderAction: Skill.Builder<S>.() -> Unit): Skill<S> =
    Skill.builder<S>().apply(builderAction).build()

fun Skill.Builder<*>.attributeModifiers(builderAction: AttributeModifierSet.Builder.() -> Unit): AttributeModifierSet =
    attributeModifierSet(builderAction).also { attributeModifiers = it }

fun Skill.Builder<*>.addBehavior(block: SkillContext<*>.() -> Unit): SkillAddBehavior =
    skillAddBehavior(block).also { addBehavior = it }

fun Skill.Builder<*>.noOpAddBehavior(): SkillAddBehavior =
    noOpSkillAddBehavior().also { addBehavior = it }

fun <S : Any> Skill.Builder<S>.cancelBehavior(block: SkillInstance<out S>.() -> Unit): SkillCancelBehavior<S> =
    skillCancelBehavior(block).also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCancelBehavior(): SkillCancelBehavior<S> =
    noOpSkillCancelBehavior<S>().also { cancelBehavior = it }

fun <S : Any> Skill.Builder<S>.completeBehavior(block: SkillInstance<out S>.() -> Unit): SkillCompleteBehavior<S> =
    skillCompleteBehavior(block).also { completeBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpCompleteBehavior(): SkillCompleteBehavior<S> =
    noOpSkillCompleteBehavior<S>().also { completeBehavior = it }

fun Skill.Builder<*>.cooldownEndBehavior(block: SkillContext<*>.() -> Unit): SkillCooldownEndBehavior =
    skillCooldownEndBehavior(block).also { cooldownEndBehavior = it }

fun Skill.Builder<*>.noOpCooldownEndBehavior(): SkillCooldownEndBehavior =
    noOpSkillCooldownEndBehavior().also { cooldownEndBehavior = it }

fun Skill.Builder<*>.cooldownStartBehavior(block: SkillContext<*>.() -> Unit): SkillCooldownStartBehavior =
    skillCooldownStartBehavior(block).also { cooldownStartBehavior = it }

fun Skill.Builder<*>.noOpCooldownStartBehavior(): SkillCooldownStartBehavior =
    noOpSkillCooldownStartBehavior().also { cooldownStartBehavior = it }

fun <S : Any> Skill.Builder<S>.endBehavior(block: SkillInstance<out S>.() -> Unit): SkillEndBehavior<S> =
    skillEndBehavior(block).also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpEndBehavior(): SkillEndBehavior<S> =
    noOpSkillEndBehavior<S>().also { endBehavior = it }

fun <S : Any> Skill.Builder<S>.interruptBehavior(block: SkillInstance<out S>.() -> Unit): SkillInterruptBehavior<S> =
    skillInterruptBehavior(block).also { interruptBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpInterruptBehavior(): SkillInterruptBehavior<S> =
    noOpSkillInterruptBehavior<S>().also { interruptBehavior = it }

fun Skill.Builder<*>.loadBehavior(block: SkillContext<*>.() -> Unit): SkillLoadBehavior =
    skillLoadBehavior(block).also { loadBehavior = it }

fun Skill.Builder<*>.noOpLoadBehavior(): SkillLoadBehavior =
    noOpSkillLoadBehavior().also { loadBehavior = it }

fun Skill.Builder<*>.removeBehavior(block: SkillContext<*>.() -> Unit): SkillRemoveBehavior =
    skillRemoveBehavior(block).also { removeBehavior = it }

fun Skill.Builder<*>.noOpRemoveBehavior(): SkillRemoveBehavior =
    noOpSkillRemoveBehavior().also { removeBehavior = it }

fun <S : Any> Skill.Builder<S>.startBehavior(block: SkillInstance<out S>.() -> Unit): SkillStartBehavior<S> =
    skillStartBehavior(block).also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpStartBehavior(): SkillStartBehavior<S> =
    noOpSkillStartBehavior<S>().also { startBehavior = it }

fun <S : Any> Skill.Builder<S>.tickBehavior(block: SkillInstance<out S>.() -> Unit): SkillTickBehavior<S> =
    skillTickBehavior(block).also { tickBehavior = it }

fun <S : Any> Skill.Builder<S>.noOpTickBehavior(): SkillTickBehavior<S> =
    noOpSkillTickBehavior<S>().also { tickBehavior = it }

fun Skill.Builder<*>.condition(block: SkillContext<*>.() -> SkillResponse): SkillCondition =
    skillCondition(block).also { condition = it }

fun Skill.Builder<*>.requireLearnedCondition(): SkillCondition =
    requireLearnedSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireNoCooldownCondition(): SkillCondition =
    requireNoCooldownSkillCondition().also { condition = it }

fun Skill.Builder<*>.requireInGameCondition(): SkillCondition =
    requireInGameSkillCondition().also { condition = it }

fun Skill.Builder<*>.defaultCondition(): SkillCondition =
    defaultSkillCondition().also { condition = it }

fun Skill.Builder<*>.durationProvider(block: SkillContext<*>.() -> Int): SkillDurationProvider =
    skillDurationProvider(block).also { durationProvider = it }

fun Skill.Builder<*>.constantDurationProvider(duration: Int): SkillDurationProvider =
    constantSkillDurationProvider(duration).also { durationProvider = it }

fun Skill.Builder<*>.infiniteDurationProvider(): SkillDurationProvider =
    infiniteSkillDurationProvider().also { durationProvider = it }

fun Skill.Builder<*>.instantDurationProvider(): SkillDurationProvider =
    instantSkillDurationProvider().also { durationProvider = it }

fun <S : Any> Skill.Builder<S>.cancelPredicate(block: SkillInstance<out S>.() -> Boolean): SkillCancelPredicate<S> =
    skillCancelPredicate(block).also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedCancelPredicate(): SkillCancelPredicate<S> =
    allowedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedCancelPredicate(): SkillCancelPredicate<S> =
    deniedSkillCancelPredicate<S>().also { cancelPredicate = it }

fun <S : Any> Skill.Builder<S>.interruptPredicate(block: SkillInstance<out S>.() -> Boolean): SkillInterruptPredicate<S> =
    skillInterruptPredicate(block).also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.allowedInterruptPredicate(): SkillInterruptPredicate<S> =
    allowedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.deniedInterruptPredicate(): SkillInterruptPredicate<S> =
    deniedSkillInterruptPredicate<S>().also { interruptPredicate = it }

fun <S : Any> Skill.Builder<S>.stateFactory(block: SkillContext<*>.() -> SkillStateCreationResult<S>): SkillStateFactory<S> =
    skillStateFactory(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.alwaysOkStateFactory(block: SkillContext<*>.() -> S): SkillStateFactory<S> =
    alwaysOkSkillStateFactory(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.alwaysErrorStateFactory(block: SkillContext<*>.() -> SkillResponse.Failure): SkillStateFactory<S> =
    alwaysErrorSkillStateFactory<S>(block).also { stateFactory = it }

fun <S : Any> Skill.Builder<S>.constantStateFactory(state: S): SkillStateFactory<S> =
    constantSkillStateFactory(state).also { stateFactory = it }