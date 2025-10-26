package org.kouv.tome.api.channeling.condition

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingInterruptCondition(crossinline block: ChannelingContext<out S>.() -> Boolean): ChannelingInterruptCondition<S> =
    ChannelingInterruptCondition { instance -> instance.block() }

fun <S : Any> alwaysAllowChannelingInterruptCondition(): ChannelingInterruptCondition<S> =
    ChannelingInterruptCondition.alwaysAllow<S>()

fun <S : Any> alwaysDenyChannelingInterruptCondition(): ChannelingInterruptCondition<S> =
    ChannelingInterruptCondition.alwaysDeny<S>()