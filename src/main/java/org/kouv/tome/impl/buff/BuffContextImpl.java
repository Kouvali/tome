package org.kouv.tome.impl.buff;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.BuffContext;

import java.util.Objects;

public final class BuffContextImpl<P> implements BuffContext<P> {
    private final Holder<? extends Buff<P>> buff;
    private final LivingEntity target;
    private final P params;

    public BuffContextImpl(
            Holder<? extends Buff<P>> buff,
            LivingEntity target,
            P params
    ) {
        this.buff = Objects.requireNonNull(buff);
        this.target = Objects.requireNonNull(target);
        this.params = Objects.requireNonNull(params);
    }

    @Override
    public Holder<? extends Buff<P>> getBuff() {
        return buff;
    }

    @Override
    public LivingEntity getTarget() {
        return target;
    }

    @Override
    public P getParams() {
        return params;
    }

    @Override
    public String toString() {
        return "BuffContextImpl{" +
                "buff=" + buff +
                ", target=" + target +
                ", params=" + params +
                '}';
    }
}
