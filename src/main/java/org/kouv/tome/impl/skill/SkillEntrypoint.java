package org.kouv.tome.impl.skill;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import org.kouv.tome.api.skill.attachment.SkillAttachments;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.api.skill.registry.SkillRegistryKeys;
import org.kouv.tome.impl.skill.command.SkillCommand;

public final class SkillEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                SkillCommand.register(dispatcher)
        );
        SkillAttachments.initialize();
        SkillRegistries.initialize();
        SkillRegistryKeys.initialize();
    }
}
