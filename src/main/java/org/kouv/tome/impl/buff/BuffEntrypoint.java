package org.kouv.tome.impl.buff;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.kouv.tome.api.buff.attachment.BuffAttachments;
import org.kouv.tome.api.buff.registry.BuffRegistries;
import org.kouv.tome.api.buff.registry.BuffRegistryKeys;
import org.kouv.tome.impl.buff.command.BuffCommand;

public final class BuffEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, _, _) ->
                BuffCommand.register(dispatcher)
        );
        BuffAttachments.initialize();
        BuffRegistries.initialize();
        BuffRegistryKeys.initialize();
    }
}
