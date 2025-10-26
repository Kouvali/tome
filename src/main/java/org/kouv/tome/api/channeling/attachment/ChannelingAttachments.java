package org.kouv.tome.api.channeling.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.channeling.manager.ChannelingManager;

public interface ChannelingAttachments {
    @SuppressWarnings("UnstableApiUsage")
    AttachmentType<ChannelingManager> CHANNELING_MANAGER = AttachmentRegistry
            .create(
                    Identifier.of("tome", "channeling_manager")
            );

    @ApiStatus.Internal
    static void initialize() {
    }
}
