package org.kouv.tome.test.buff.event;

import net.fabricmc.api.ModInitializer;
import net.minecraft.world.effect.MobEffects;
import org.kouv.tome.api.buff.event.BuffTestCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class BuffEventTest implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(BuffEventTest.class);

    @Override
    public void onInitialize() {
        BuffTestCallback.EVENT.register(context -> {
            boolean result = !context.getTarget().hasEffect(MobEffects.WEAKNESS);
            LOGGER.info(
                    "BuffTestCallback called: context={}, result={}",
                    context,
                    result
            );
            return result;
        });
    }
}
