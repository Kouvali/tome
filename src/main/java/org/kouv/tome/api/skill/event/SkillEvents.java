package org.kouv.tome.api.skill.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import org.kouv.tome.api.skill.SkillContext;

public interface SkillEvents {
    Event<Loaded> LOADED = EventFactory.createArrayBacked(Loaded.class, callbacks -> context -> {
        for (Loaded callback : callbacks) {
            callback.onLoaded(context);
        }
    });

    Event<Added> ADDED = EventFactory.createArrayBacked(Added.class, callbacks -> context -> {
        for (Added callback : callbacks) {
            callback.onAdded(context);
        }
    });

    Event<Removed> REMOVED = EventFactory.createArrayBacked(Removed.class, callbacks -> context -> {
        for (Removed callback : callbacks) {
            callback.onRemoved(context);
        }
    });

    Event<CooldownStarted> COOLDOWN_STARTED = EventFactory.createArrayBacked(CooldownStarted.class, callbacks -> context -> {
        for (CooldownStarted callback : callbacks) {
            callback.onCooldownStarted(context);
        }
    });

    Event<CooldownEnded> COOLDOWN_ENDED = EventFactory.createArrayBacked(CooldownEnded.class, callbacks -> context -> {
        for (CooldownEnded callback : callbacks) {
            callback.onCooldownEnded(context);
        }
    });

    @FunctionalInterface
    interface Loaded {
        void onLoaded(SkillContext<?> context);
    }

    @FunctionalInterface
    interface Added {
        void onAdded(SkillContext<?> context);
    }

    @FunctionalInterface
    interface Removed {
        void onRemoved(SkillContext<?> context);
    }

    @FunctionalInterface
    interface CooldownStarted {
        void onCooldownStarted(SkillContext<?> context);
    }

    @FunctionalInterface
    interface CooldownEnded {
        void onCooldownEnded(SkillContext<?> context);
    }
}
