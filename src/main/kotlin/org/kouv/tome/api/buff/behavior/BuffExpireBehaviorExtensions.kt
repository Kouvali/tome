package org.kouv.tome.api.buff.behavior

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.BuffInstance

@ApiStatus.Experimental
fun <P : Any> buffExpireBehavior(block: BuffInstance<out P>.() -> Unit): BuffExpireBehavior<P> =
    BuffExpireBehavior(block)

@ApiStatus.Experimental
fun <P : Any> noOpBuffExpireBehavior(): BuffExpireBehavior<P> = BuffExpireBehavior.noOp()

@ApiStatus.Experimental
operator fun <P : Any> BuffExpireBehavior<P>.plus(other: BuffExpireBehavior<P>): BuffExpireBehavior<P> = andThen(other)