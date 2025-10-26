package org.kouv.tome.api.channeling.behavior

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingInterruptBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingInterruptBehavior<S> =
    ChannelingInterruptBehavior { context -> context.block() }

fun <S : Any> noOpChannelingInterruptBehavior(): ChannelingInterruptBehavior<S> = ChannelingInterruptBehavior.noOp<S>()