package org.kouv.tome.api.skill.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.kouv.tome.api.skill.SkillContext;

@FunctionalInterface
public interface SkillRemovedCallback {
    Event<SkillRemovedCallback> EVENT = EventFactory.createArrayBacked(SkillRemovedCallback.class, callbacks -> context -> {
        for (SkillRemovedCallback callback : callbacks) {
            callback.onRemoved(context);
        }
    });

    void onRemoved(SkillContext<?> context);
}
