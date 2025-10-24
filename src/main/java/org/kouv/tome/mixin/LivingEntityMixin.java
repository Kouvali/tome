package org.kouv.tome.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.kouv.tome.api.channeling.attachment.ChannelingAttachments;
import org.kouv.tome.api.channeling.entity.ChannelingEntity;
import org.kouv.tome.api.channeling.manager.ChannelingManager;
import org.kouv.tome.impl.channeling.manager.ChannelingManagerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements ChannelingEntity {
    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public ChannelingManager getChannelingManager() {
        return getAttachedOrCreate(ChannelingAttachments.CHANNELING_MANAGER, () -> new ChannelingManagerImpl((LivingEntity) (Object) this));
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

    @Inject(method = "onRemove", at = @At(value = "TAIL"))
    private void tome$onRemove(RemovalReason reason, CallbackInfo ci) {
        if (getEntityWorld().isClient()) {
            return;
        }

        ChannelingManager channelingManager = getChannelingManager();
        if (!channelingManager.interruptChanneling()) {
            ((ChannelingManagerImpl) channelingManager).discard();
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tome$tick(CallbackInfo ci) {
        if (getEntityWorld().isClient()) {
            return;
        }

        ((ChannelingManagerImpl) getChannelingManager()).update();
    }
}
