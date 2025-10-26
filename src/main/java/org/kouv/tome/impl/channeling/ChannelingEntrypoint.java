package org.kouv.tome.impl.channeling;

import net.fabricmc.api.ModInitializer;
import org.kouv.tome.api.channeling.attachment.ChannelingAttachments;

public final class ChannelingEntrypoint implements ModInitializer {
    @Override
    public void onInitialize() {
        ChannelingAttachments.initialize();
    }
}
