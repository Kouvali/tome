package org.kouv.tome.api.channeling.behavior

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingEndBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingEndBehavior<S> =
    ChannelingEndBehavior { context -> context.block() }

fun <S : Any> noOpChannelingEndBehavior(): ChannelingEndBehavior<S> = ChannelingEndBehavior.noOp<S>()