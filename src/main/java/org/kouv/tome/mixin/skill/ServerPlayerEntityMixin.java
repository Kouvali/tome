package org.kouv.tome.mixin.skill;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @Inject(method = "onDeath", at = @At(value = "TAIL"))
    private void tome$onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (tome$serverPlayerEntity().getWorld().isClient()) {
            return;
        }

        SkillManager skillManager = tome$serverPlayerEntity().getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Unique
    private ServerPlayerEntity tome$serverPlayerEntity() {
        return (ServerPlayerEntity) (Object) this;
    }
}
