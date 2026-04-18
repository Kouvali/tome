package org.kouv.tome.api.buff.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.BuffContext;

@ApiStatus.Experimental
@FunctionalInterface
public interface BuffTestCallback {
    Event<BuffTestCallback> EVENT = EventFactory.createArrayBacked(BuffTestCallback.class, callbacks -> context -> {
        for (BuffTestCallback callback : callbacks) {
            if (!callback.onTest(context)) {
                return false;
            }
        }

        return true;
    });

    boolean onTest(BuffContext<?> context);
}
