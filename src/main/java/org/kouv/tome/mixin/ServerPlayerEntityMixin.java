package org.kouv.tome.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.kouv.tome.api.channeling.manager.ChannelingManager;
import org.kouv.tome.impl.channeling.manager.ChannelingManagerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity {
    private ServerPlayerEntityMixin(World world, GameProfile profile) {
        super(world, profile);
    }

    @Inject(method = "onDeath", at = @At(value = "TAIL"))
    private void tome$onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (getEntityWorld().isClient()) {
            return;
        }

        ChannelingManager channelingManager = getChannelingManager();
        if (!channelingManager.interruptChanneling()) {
            ((ChannelingManagerImpl) channelingManager).discard();
        }
    }
}
