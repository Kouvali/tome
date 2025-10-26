package org.kouv.tome.api.channeling;

import net.minecraft.entity.LivingEntity;

public interface ChannelingContext<S> {
    Channeling<S> getChanneling();

    S getState();

    LivingEntity getSource();
}
