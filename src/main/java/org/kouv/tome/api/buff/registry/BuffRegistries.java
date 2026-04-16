package org.kouv.tome.api.buff.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.Buff;

@ApiStatus.Experimental
public interface BuffRegistries {
    Registry<Buff<?>> BUFF = FabricRegistryBuilder.create(BuffRegistryKeys.BUFF).buildAndRegister();

    @ApiStatus.Internal
    static void initialize() {
    }
}
