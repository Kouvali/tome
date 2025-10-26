package org.kouv.tome.impl.channeling;

import net.minecraft.entity.LivingEntity;
import org.kouv.tome.api.channeling.Channeling;
import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

public final class ChannelingContextImpl<S> implements ChannelingContext<S> {
    private final Channeling<S> channeling;
    private final S state;
    private final LivingEntity source;

    public ChannelingContextImpl(
            Channeling<S> channeling,
            S state,
            LivingEntity source
    ) {
        this.channeling = Objects.requireNonNull(channeling);
        this.state = Objects.requireNonNull(state);
        this.source = Objects.requireNonNull(source);
    }

    @Override
    public Channeling<S> getChanneling() {
        return channeling;
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public LivingEntity getSource() {
        return source;
    }
}
