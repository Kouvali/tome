package org.kouv.tome.impl.buff;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.BuffInstance;
import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;

import java.util.Objects;

public final class BuffInstanceImpl<P> implements BuffInstance<P> {
    private final Holder<? extends Buff<P>> buff;
    private final LivingEntity target;
    private P params;

    private final AttributeModifierTracker attributeModifierTracker;
    private int duration;

    private boolean markedForExpiration;
    private int elapsedTime;

    public BuffInstanceImpl(Holder<? extends Buff<P>> buff, LivingEntity target, P params, AttributeModifierTracker attributeModifierTracker, int duration) {
        this.buff = Objects.requireNonNull(buff);
        this.target = Objects.requireNonNull(target);
        this.params = Objects.requireNonNull(params);
        this.attributeModifierTracker = Objects.requireNonNull(attributeModifierTracker);
        this.duration = duration;
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
    public void setParams(P params) {
        this.params = Objects.requireNonNull(params);
    }

    @Override
    public AttributeModifierTracker getAttributeModifierTracker() {
        return attributeModifierTracker;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean isMarkedForExpiration() {
        return markedForExpiration;
    }

    @Override
    public void setMarkedForExpiration(boolean markedForExpiration) {
        this.markedForExpiration = markedForExpiration;
    }

    @Override
    public int getElapsedTime() {
        return elapsedTime;
    }

    @Override
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void update() {
        elapsedTime++;
    }

    @Override
    public String toString() {
        return "BuffInstanceImpl{" +
                "buff=" + buff +
                ", target=" + target +
                ", params=" + params +
                ", attributeModifierTracker=" + attributeModifierTracker +
                ", duration=" + duration +
                ", markedForExpiration=" + markedForExpiration +
                ", elapsedTime=" + elapsedTime +
                '}';
    }
}
