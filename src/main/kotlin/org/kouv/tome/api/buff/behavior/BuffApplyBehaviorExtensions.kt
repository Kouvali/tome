package org.kouv.tome.api.buff.behavior

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.BuffInstance

@ApiStatus.Experimental
fun <P : Any> buffApplyBehavior(block: BuffInstance<out P>.() -> Unit): BuffApplyBehavior<P> =
    BuffApplyBehavior(block)

@ApiStatus.Experimental
fun <P : Any> noOpBuffApplyBehavior(): BuffApplyBehavior<P> = BuffApplyBehavior.noOp()

@ApiStatus.Experimental
operator fun <P : Any> BuffApplyBehavior<P>.plus(other: BuffApplyBehavior<P>): BuffApplyBehavior<P> = andThen(other)