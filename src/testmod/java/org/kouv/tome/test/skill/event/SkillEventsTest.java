package org.kouv.tome.test.skill.event;

import net.fabricmc.api.ModInitializer;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillEventsTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillEventsTest.class);

    @Override
    public void onInitialize() {
        SkillEvents.LOADED.register(new TestLoaded());
        SkillEvents.ADDED.register(new TestAdded());
        SkillEvents.REMOVED.register(new TestRemoved());
        SkillEvents.COOLDOWN_STARTED.register(new TestCooldownStarted());
        SkillEvents.COOLDOWN_ENDED.register(new TestCooldownEnded());
    }

    public static final class TestLoaded implements SkillEvents.Loaded {
        @Override
        public void onLoaded(SkillContext<?> context) {
            LOGGER.info("skill loaded: context={}", context);
        }
    }

    public static final class TestAdded implements SkillEvents.Added {
        @Override
        public void onAdded(SkillContext<?> context) {
            LOGGER.info("skill added: context={}", context);
        }
    }

    public static final class TestRemoved implements SkillEvents.Removed {
        @Override
        public void onRemoved(SkillContext<?> context) {
            LOGGER.info("skill removed: context={}", context);
        }
    }

    public static final class TestCooldownStarted implements SkillEvents.CooldownStarted {
        @Override
        public void onCooldownStarted(SkillContext<?> context) {
            LOGGER.info("skill cooldown started: context={}", context);
        }
    }

    public static final class TestCooldownEnded implements SkillEvents.CooldownEnded {
        @Override
        public void onCooldownEnded(SkillContext<?> context) {
            LOGGER.info("skill cooldown ended: context={}", context);
        }
    }
}
