package org.kouv.tome.api.buff.manager;

import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.BuffInstance;

import java.util.Collection;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

@ApiStatus.Experimental
public interface BuffManager {
    Collection<? extends BuffInstance<?>> getBuffInstances();

    boolean hasBuff(Holder<? extends Buff<?>> buff);

    <P> @Nullable BuffInstance<P> getBuffInstance(Holder<? extends Buff<P>> buff);

    <P> boolean applyBuff(
            Holder<? extends Buff<P>> buff,
            P params,
            int duration
    );

    <P> boolean updateBuff(
            Holder<? extends Buff<P>> buff,
            UnaryOperator<P> paramsMapper,
            IntUnaryOperator durationMapper
    );

    boolean removeBuff(Holder<? extends Buff<?>> buff);

    void clearBuffs();
}
