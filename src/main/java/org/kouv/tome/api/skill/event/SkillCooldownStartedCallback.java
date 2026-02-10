package org.kouv.tome.api.skill.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.kouv.tome.api.skill.SkillContext;

@FunctionalInterface
public interface SkillCooldownStartedCallback {
    Event<SkillCooldownStartedCallback> EVENT = EventFactory.createArrayBacked(SkillCooldownStartedCallback.class, callbacks -> context -> {
        for (SkillCooldownStartedCallback callback : callbacks) {
            callback.onCooldownStarted(context);
        }
    });

    void onCooldownStarted(SkillContext<?> context);
}
