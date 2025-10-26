package org.kouv.tome.api.channeling.behavior

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingCancelBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingCancelBehavior<S> =
    ChannelingCancelBehavior { context -> context.block() }

fun <S : Any> noOpChannelingCancelBehavior(): ChannelingCancelBehavior<S> = ChannelingCancelBehavior.noOp<S>()