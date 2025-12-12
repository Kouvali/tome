package org.kouv.tome.test.skill.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillEventsTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillEventsTest.class);

    @Override
    public void onInitialize() {
        SkillEvents.ADDED.register(new TestAdded());
        SkillEvents.REMOVED.register(new TestRemoved());
        SkillEvents.COOLDOWN_STARTED.register(new TestCooldownStarted());
        SkillEvents.COOLDOWN_ENDED.register(new TestCooldownEnded());
    }

    public static final class TestAdded implements SkillEvents.Added {
        @Override
        public void onAdded(LivingEntity source, RegistryEntry<? extends Skill<?>> skill) {
            LOGGER.info("skill added: source={}, skill={}", source, skill);
        }
    }

    public static final class TestRemoved implements SkillEvents.Removed {
        @Override
        public void onRemoved(LivingEntity source, RegistryEntry<? extends Skill<?>> skill) {
            LOGGER.info("skill removed: source={}, skill={}", source, skill);
        }
    }

    public static final class TestCooldownStarted implements SkillEvents.CooldownStarted {
        @Override
        public void onCooldownStarted(LivingEntity source, Identifier id) {
            LOGGER.info("skill cooldown started: source={}, id={}", source, id);
        }
    }

    public static final class TestCooldownEnded implements SkillEvents.CooldownEnded {
        @Override
        public void onCooldownEnded(LivingEntity source, Identifier id) {
            LOGGER.info("skill cooldown ended: source={}, id={}", source, id);
        }
    }
}
