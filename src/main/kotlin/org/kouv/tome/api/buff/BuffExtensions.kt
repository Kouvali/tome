package org.kouv.tome.api.buff

import org.jetbrains.annotations.ApiStatus
import org.kouv.tome.api.buff.behavior.*

@ApiStatus.Experimental
fun <P : Any> buff(builderAction: Buff.Builder<P>.() -> Unit): Buff<P> =
    Buff.builder<P>().apply(builderAction).build()

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.applyBehavior(block: BuffInstance<out P>.() -> Unit): BuffApplyBehavior<P> =
    buffApplyBehavior(block).also { applyBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.noOpApplyBehavior(): BuffApplyBehavior<P> =
    noOpBuffApplyBehavior<P>().also { applyBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.endBehavior(block: BuffInstance<out P>.() -> Unit): BuffEndBehavior<P> =
    buffEndBehavior(block).also { endBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.noOpEndBehavior(): BuffEndBehavior<P> =
    noOpBuffEndBehavior<P>().also { endBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.expireBehavior(block: BuffInstance<out P>.() -> Unit): BuffExpireBehavior<P> =
    buffExpireBehavior(block).also { expireBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.noOpExpireBehavior(): BuffExpireBehavior<P> =
    noOpBuffExpireBehavior<P>().also { expireBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.removeBehavior(block: BuffInstance<out P>.() -> Unit): BuffRemoveBehavior<P> =
    buffRemoveBehavior(block).also { removeBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.noOpRemoveBehavior(): BuffRemoveBehavior<P> =
    noOpBuffRemoveBehavior<P>().also { removeBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.tickBehavior(block: BuffInstance<out P>.() -> Unit): BuffTickBehavior<P> =
    buffTickBehavior(block).also { tickBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.noOpTickBehavior(): BuffTickBehavior<P> =
    noOpBuffTickBehavior<P>().also { tickBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.updateBehavior(block: BuffInstance<out P>.() -> Unit): BuffUpdateBehavior<P> =
    buffUpdateBehavior(block).also { updateBehavior = it }

@ApiStatus.Experimental
fun <P : Any> Buff.Builder<P>.noOpUpdateBehavior(): BuffUpdateBehavior<P> =
    noOpBuffUpdateBehavior<P>().also { updateBehavior = it }