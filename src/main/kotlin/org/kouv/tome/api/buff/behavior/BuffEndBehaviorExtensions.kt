package org.kouv.tome.api.buff.behavior

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.BuffInstance

@ApiStatus.Experimental
fun <P : Any> buffEndBehavior(block: BuffInstance<out P>.() -> Unit): BuffEndBehavior<P> =
    BuffEndBehavior(block)

@ApiStatus.Experimental
fun <P : Any> noOpBuffEndBehavior(): BuffEndBehavior<P> = BuffEndBehavior.noOp()

@ApiStatus.Experimental
operator fun <P : Any> BuffEndBehavior<P>.plus(other: BuffEndBehavior<P>): BuffEndBehavior<P> = andThen(other)