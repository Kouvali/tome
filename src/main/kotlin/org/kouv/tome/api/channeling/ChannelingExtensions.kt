package org.kouv.tome.api.channeling

import org.kouv.tome.api.channeling.behavior.*
import org.kouv.tome.api.channeling.condition.*

inline fun <S : Any> channeling(builderAction: Channeling.Builder<out S>.() -> Unit): Channeling<S> =
    Channeling.builder<S>().apply(builderAction).build()

inline fun <S : Any> Channeling.Builder<S>.cancelBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingCancelBehavior<S> =
    channelingCancelBehavior(block).also { cancelBehavior = it }

fun <S : Any> Channeling.Builder<S>.noOpCancelBehavior(): ChannelingCancelBehavior<S> =
    noOpChannelingCancelBehavior<S>().also { cancelBehavior = it }

inline fun <S : Any> Channeling.Builder<S>.completeBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingCompleteBehavior<S> =
    channelingCompleteBehavior(block).also { completeBehavior = it }

fun <S : Any> Channeling.Builder<S>.noOpCompleteBehavior(): ChannelingCompleteBehavior<S> =
    noOpChannelingCompleteBehavior<S>().also { completeBehavior = it }

inline fun <S : Any> Channeling.Builder<S>.endBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingEndBehavior<S> =
    channelingEndBehavior(block).also { endBehavior = it }

fun <S : Any> Channeling.Builder<S>.noOpEndBehavior(): ChannelingEndBehavior<S> =
    noOpChannelingEndBehavior<S>().also { endBehavior = it }

inline fun <S : Any> Channeling.Builder<S>.interruptBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingInterruptBehavior<S> =
    channelingInterruptBehavior(block).also { interruptBehavior = it }

fun <S : Any> Channeling.Builder<S>.noOpInterruptBehavior(): ChannelingInterruptBehavior<S> =
    noOpChannelingInterruptBehavior<S>().also { interruptBehavior = it }

inline fun <S : Any> Channeling.Builder<S>.startBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingStartBehavior<S> =
    channelingStartBehavior(block).also { startBehavior = it }

fun <S : Any> Channeling.Builder<S>.noOpStartBehavior(): ChannelingStartBehavior<S> =
    noOpChannelingStartBehavior<S>().also { startBehavior = it }

inline fun <S : Any> Channeling.Builder<S>.tickBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingTickBehavior<S> =
    channelingTickBehavior(block).also { tickBehavior = it }

fun <S : Any> Channeling.Builder<S>.noOpTickBehavior(): ChannelingTickBehavior<S> =
    noOpChannelingTickBehavior<S>().also { tickBehavior = it }

inline fun <S : Any> Channeling.Builder<S>.cancelCondition(crossinline block: ChannelingContext<out S>.() -> Boolean): ChannelingCancelCondition<S> =
    channelingCancelCondition(block).also { cancelCondition = it }

fun <S : Any> Channeling.Builder<S>.alwaysAllowCancelCondition(): ChannelingCancelCondition<S> =
    alwaysAllowChannelingCancelCondition<S>().also { cancelCondition = it }

fun <S : Any> Channeling.Builder<S>.alwaysDenyCancelCondition(): ChannelingCancelCondition<S> =
    alwaysDenyChannelingCancelCondition<S>().also { cancelCondition = it }

inline fun <S : Any> Channeling.Builder<S>.interruptCondition(crossinline block: ChannelingContext<out S>.() -> Boolean): ChannelingInterruptCondition<S> =
    channelingInterruptCondition(block).also { interruptCondition = it }

fun <S : Any> Channeling.Builder<S>.alwaysAllowInterruptCondition(): ChannelingInterruptCondition<S> =
    alwaysAllowChannelingInterruptCondition<S>().also { interruptCondition = it }

fun <S : Any> Channeling.Builder<S>.alwaysDenyInterruptCondition(): ChannelingInterruptCondition<S> =
    alwaysDenyChannelingInterruptCondition<S>().also { interruptCondition = it }