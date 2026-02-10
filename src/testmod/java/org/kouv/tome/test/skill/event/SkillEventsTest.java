package org.kouv.tome.test.skill.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.effect.MobEffects;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.event.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SkillEventsTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(SkillEventsTest.class);

    @Override
    public void onInitialize() {
        SkillTestCallback.EVENT.register(context -> {
            SkillResponse response = context.getSource().hasEffect(MobEffects.BLINDNESS) ?
                    SkillResponse.unavailable() :
                    SkillResponse.success();
            LOGGER.info(
                    "SkillTestCallback called: context={}, response={}",
                    context,
                    response
            );
            return response;
        });
        SkillLoadedCallback.EVENT.register(context ->
                LOGGER.info(
                        "SkillLoadedCallback called: context={}",
                        context
                )
        );
        SkillAddedCallback.EVENT.register(context ->
                LOGGER.info(
                        "SkillAddedCallback called: context={}",
                        context
                )
        );
        SkillRemovedCallback.EVENT.register(context ->
                LOGGER.info(
                        "SkillRemovedCallback called: context={}",
                        context
                )
        );
        SkillCooldownStartedCallback.EVENT.register(context ->
                LOGGER.info(
                        "SkillCooldownStartedCallback called: context={}",
                        context
                )
        );
        SkillCooldownEndedCallback.EVENT.register(context ->
                LOGGER.info(
                        "SkillCooldownEndedCallback called: context={}",
                        context
                )
        );
    }
}
