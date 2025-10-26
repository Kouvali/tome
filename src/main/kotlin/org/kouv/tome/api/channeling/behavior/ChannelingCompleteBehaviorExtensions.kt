package org.kouv.tome.api.channeling.behavior

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingCompleteBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingCompleteBehavior<S> =
    ChannelingCompleteBehavior { context -> context.block() }

fun <S : Any> noOpChannelingCompleteBehavior(): ChannelingCompleteBehavior<S> = ChannelingCompleteBehavior.noOp<S>()