package org.kouv.tome.api.skill.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.kouv.tome.api.skill.SkillContext;

@FunctionalInterface
public interface SkillCooldownEndedCallback {
    Event<SkillCooldownEndedCallback> EVENT = EventFactory.createArrayBacked(SkillCooldownEndedCallback.class, callbacks -> context -> {
        for (SkillCooldownEndedCallback callback : callbacks) {
            callback.onCooldownEnded(context);
        }
    });

    void onCooldownEnded(SkillContext<?> context);
}
