package org.kouv.tome.api.channeling.manager;

import org.kouv.tome.api.channeling.Channeling;

public interface ChannelingManager {
    boolean isChanneling();

    <S> boolean startChanneling(Channeling<S> channeling, S state);

    boolean completeChanneling();

    boolean cancelChanneling();

    boolean interruptChanneling();
}
