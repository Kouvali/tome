package org.kouv.tome.api.channeling.behavior

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingTickBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingTickBehavior<S> =
    ChannelingTickBehavior { context -> context.block() }

fun <S : Any> noOpChannelingTickBehavior(): ChannelingTickBehavior<S> = ChannelingTickBehavior.noOp<S>()