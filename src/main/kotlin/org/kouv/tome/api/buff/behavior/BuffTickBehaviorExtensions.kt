package org.kouv.tome.api.buff.behavior

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.BuffInstance

@ApiStatus.Experimental
fun <P : Any> buffTickBehavior(block: BuffInstance<out P>.() -> Unit): BuffTickBehavior<P> =
    BuffTickBehavior(block)

@ApiStatus.Experimental
fun <P : Any> noOpBuffTickBehavior(): BuffTickBehavior<P> = BuffTickBehavior.noOp()

@ApiStatus.Experimental
operator fun <P : Any> BuffTickBehavior<P>.plus(other: BuffTickBehavior<P>): BuffTickBehavior<P> = andThen(other)