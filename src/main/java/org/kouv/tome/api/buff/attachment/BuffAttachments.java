package org.kouv.tome.api.buff.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.manager.BuffManager;

@ApiStatus.Experimental
public interface BuffAttachments {
    AttachmentType<BuffManager> BUFF_MANAGER = AttachmentRegistry
            .create(
                    Identifier.fromNamespaceAndPath("tome", "buff_manager")
            );

    @ApiStatus.Internal
    static void initialize() {
    }
}
