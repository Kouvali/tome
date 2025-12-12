package org.kouv.tome.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.attachment.SkillAttachments;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.kouv.tome.impl.skill.manager.SkillManagerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

        SkillManager skillManager = tome$getSkillManagerOrNull();
        if (skillManager != null && !skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    @Unique
    private @Nullable SkillManager tome$getSkillManagerOrNull() {
        SkillManagerImpl skillManager =
                (SkillManagerImpl) getAttached(SkillAttachments.SKILL_MANAGER);

        if (skillManager != null && skillManager.getSource() != this) {
            skillManager.setSource(this);
        }

        return skillManager;
    }
}
