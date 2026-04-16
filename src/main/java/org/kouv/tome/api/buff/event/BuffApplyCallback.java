package org.kouv.tome.api.buff.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.BuffContext;

@ApiStatus.Experimental
@FunctionalInterface
public interface BuffApplyCallback {
    Event<BuffApplyCallback> EVENT = EventFactory.createArrayBacked(BuffApplyCallback.class, callbacks -> context -> {
        for (BuffApplyCallback callback : callbacks) {
            if (!callback.onApply(context)) {
                return false;
            }
        }

        return true;
    });

    boolean onApply(BuffContext<?> context);
}
