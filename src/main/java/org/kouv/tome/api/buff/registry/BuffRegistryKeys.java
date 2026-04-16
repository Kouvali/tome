package org.kouv.tome.api.buff.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.Buff;

@ApiStatus.Experimental
public interface BuffRegistryKeys {
    ResourceKey<Registry<Buff<?>>> BUFF = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath("tome", "buff"));

    @ApiStatus.Internal
    static void initialize() {
    }
}
