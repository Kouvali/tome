package org.kouv.tome.api.channeling.manager;

import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.channeling.Channeling;
import org.kouv.tome.api.channeling.ChannelingContext;

public interface ChannelingManager {
    boolean isChanneling();

    <S> @Nullable ChannelingContext<S> getChannelingContext(Channeling<S> channeling);

    <S> @Nullable ChannelingContext<S> getChannelingContext(Class<S> clazz);

    <S> boolean startChanneling(Channeling<S> channeling, S state);

    boolean completeChanneling();

    boolean cancelChanneling();

    boolean interruptChanneling();
}
