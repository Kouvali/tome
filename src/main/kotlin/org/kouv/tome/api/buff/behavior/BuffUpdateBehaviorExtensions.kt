package org.kouv.tome.api.buff.behavior

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.BuffInstance

@ApiStatus.Experimental
fun <P : Any> buffUpdateBehavior(block: BuffInstance<out P>.() -> Unit): BuffUpdateBehavior<P> =
    BuffUpdateBehavior(block)

@ApiStatus.Experimental
fun <P : Any> noOpBuffUpdateBehavior(): BuffUpdateBehavior<P> = BuffUpdateBehavior.noOp()

@ApiStatus.Experimental
operator fun <P : Any> BuffUpdateBehavior<P>.plus(other: BuffUpdateBehavior<P>): BuffUpdateBehavior<P> = andThen(other)