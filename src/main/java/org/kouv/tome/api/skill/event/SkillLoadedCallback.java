package org.kouv.tome.api.skill.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.kouv.tome.api.skill.SkillContext;

@FunctionalInterface
public interface SkillLoadedCallback {
    Event<SkillLoadedCallback> EVENT = EventFactory.createArrayBacked(SkillLoadedCallback.class, callbacks -> context -> {
        for (SkillLoadedCallback callback : callbacks) {
            callback.onLoaded(context);
        }
    });

    void onLoaded(SkillContext<?> context);
}
