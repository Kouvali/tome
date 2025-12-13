package org.kouv.tome.api.skill.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;

public interface SkillEvents {
    Event<Added> ADDED = EventFactory.createArrayBacked(Added.class, callbacks -> (entity, skill) -> {
        for (Added callback : callbacks) {
            callback.onAdded(entity, skill);
        }
    });

    Event<Removed> REMOVED = EventFactory.createArrayBacked(Removed.class, callbacks -> (entity, skill) -> {
        for (Removed callback : callbacks) {
            callback.onRemoved(entity, skill);
        }
    });

    Event<CooldownStarted> COOLDOWN_STARTED = EventFactory.createArrayBacked(CooldownStarted.class, callbacks -> (entity, id) -> {
        for (CooldownStarted callback : callbacks) {
            callback.onCooldownStarted(entity, id);
        }
    });

    Event<CooldownEnded> COOLDOWN_ENDED = EventFactory.createArrayBacked(CooldownEnded.class, callbacks -> (entity, id) -> {
        for (CooldownEnded callback : callbacks) {
            callback.onCooldownEnded(entity, id);
        }
    });

    @FunctionalInterface
    interface Added {
        void onAdded(LivingEntity source, RegistryEntry<? extends Skill<?>> skill);
    }

    @FunctionalInterface
    interface Removed {
        void onRemoved(LivingEntity source, RegistryEntry<? extends Skill<?>> skill);
    }

    @FunctionalInterface
    interface CooldownStarted {
        void onCooldownStarted(LivingEntity source, RegistryEntry<? extends Skill<?>> skill);
    }

    @FunctionalInterface
    interface CooldownEnded {
        void onCooldownEnded(LivingEntity source, RegistryEntry<? extends Skill<?>> skill);
    }
}
