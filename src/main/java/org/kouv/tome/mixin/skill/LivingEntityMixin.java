package org.kouv.tome.mixin.skill;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import org.kouv.tome.api.skill.attachment.SkillAttachments;
import org.kouv.tome.api.skill.entity.SkillSource;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.kouv.tome.impl.skill.manager.SkillContainerImpl;
import org.kouv.tome.impl.skill.manager.SkillCooldownManagerImpl;
import org.kouv.tome.impl.skill.manager.SkillManagerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin implements SkillSource {
    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public SkillContainer getSkillContainer() {
        return tome$livingEntity().getAttachedOrCreate(SkillAttachments.SKILL_CONTAINER, SkillContainerImpl::new);
    }

    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public SkillCooldownManager getSkillCooldownManager() {
        return tome$livingEntity().getAttachedOrCreate(SkillAttachments.SKILL_COOLDOWN_MANAGER, SkillCooldownManagerImpl::new);
    }

    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public SkillManager getSkillManager() {
        return tome$livingEntity().getAttachedOrCreate(SkillAttachments.SKILL_MANAGER, () -> new SkillManagerImpl(tome$livingEntity()));
    }

    @Inject(method = "onDeath", at = @At(value = "TAIL"))
    private void tome$onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (tome$livingEntity().getWorld().isClient()) {
            return;
        }

        SkillManager skillManager = getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Inject(method = "onRemove", at = @At(value = "TAIL"))
    private void tome$onRemove(Entity.RemovalReason reason, CallbackInfo ci) {
        if (tome$livingEntity().getWorld().isClient()) {
            return;
        }

        SkillManager skillManager = getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tome$tick(CallbackInfo ci) {
        if (tome$livingEntity().getWorld().isClient()) {
            return;
        }

        ((SkillCooldownManagerImpl) getSkillCooldownManager()).update();
        ((SkillManagerImpl) getSkillManager()).update();
    }

    @Unique
    private LivingEntity tome$livingEntity() {
        return (LivingEntity) (Object) this;
    }
}
