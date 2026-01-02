package org.kouv.tome.test.skill.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.effect.MobEffects;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillEventsTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillEventsTest.class);

    @Override
    public void onInitialize() {
        SkillEvents.TEST.register(context -> {
            SkillResponse response = context.getSource().hasEffect(MobEffects.BLINDNESS) ?
                    SkillResponse.unavailable() :
                    SkillResponse.success();
            LOGGER.info(
                    "Test called: context={}, response={}",
                    context,
                    response
            );
            return response;
        });
        SkillEvents.LOADED.register(context ->
                LOGGER.info(
                        "Loaded called: context={}",
                        context
                )
        );
        SkillEvents.ADDED.register(context ->
                LOGGER.info(
                        "Added called: context={}",
                        context
                )
        );
        SkillEvents.REMOVED.register(context ->
                LOGGER.info(
                        "Removed called: context={}",
                        context
                )
        );
        SkillEvents.COOLDOWN_STARTED.register(context ->
                LOGGER.info(
                        "CooldownStarted called: context={}",
                        context
                )
        );
        SkillEvents.COOLDOWN_ENDED.register(context ->
                LOGGER.info(
                        "CooldownEnded called: context={}",
                        context
                )
        );
    }
}
