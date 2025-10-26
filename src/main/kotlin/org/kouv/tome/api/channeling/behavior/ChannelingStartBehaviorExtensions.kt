package org.kouv.tome.api.channeling.behavior

import org.kouv.tome.api.channeling.ChannelingContext

inline fun <S : Any> channelingStartBehavior(crossinline block: ChannelingContext<out S>.() -> Unit): ChannelingStartBehavior<S> =
    ChannelingStartBehavior { context -> context.block() }

fun <S : Any> noOpChannelingStartBehavior(): ChannelingStartBehavior<S> = ChannelingStartBehavior.noOp<S>()