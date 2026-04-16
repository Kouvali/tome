package org.kouv.tome.api.buff.behavior

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.BuffInstance

@ApiStatus.Experimental
fun <P : Any> buffRemoveBehavior(block: BuffInstance<out P>.() -> Unit): BuffRemoveBehavior<P> =
    BuffRemoveBehavior(block)

@ApiStatus.Experimental
fun <P : Any> noOpBuffRemoveBehavior(): BuffRemoveBehavior<P> = BuffRemoveBehavior.noOp()

@ApiStatus.Experimental
operator fun <P : Any> BuffRemoveBehavior<P>.plus(other: BuffRemoveBehavior<P>): BuffRemoveBehavior<P> = andThen(other)