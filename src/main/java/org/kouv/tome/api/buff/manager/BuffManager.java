package org.kouv.tome.api.buff.manager;

import net.minecraft.core.Holder;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.BuffInstance;

import java.util.Collection;
import java.util.function.Consumer;

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
            Consumer<? super BuffInstance<P>> updater
    );

    boolean removeBuff(Holder<? extends Buff<?>> buff);

    void clearBuffs();
}
