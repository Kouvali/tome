package org.kouv.tome.api.channeling.condition

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingCancelCondition(crossinline block: ChannelingContext<out S>.() -> Boolean): ChannelingCancelCondition<S> =
    ChannelingCancelCondition { instance -> instance.block() }

fun <S : Any> alwaysAllowChannelingCancelCondition(): ChannelingCancelCondition<S> =
    ChannelingCancelCondition.alwaysAllow<S>()

fun <S : Any> alwaysDenyChannelingCancelCondition(): ChannelingCancelCondition<S> =
    ChannelingCancelCondition.alwaysDeny<S>()