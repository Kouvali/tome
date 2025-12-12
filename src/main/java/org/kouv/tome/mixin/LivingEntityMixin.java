package org.kouv.tome.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.world.World;
import org.kouv.tome.api.skill.attachment.SkillAttachments;
import org.kouv.tome.api.skill.entity.SkillEntity;
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
public abstract class LivingEntityMixin extends Entity implements SkillEntity {
    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public SkillContainer getSkillContainer() {
        SkillContainerImpl skillContainer =
                (SkillContainerImpl) getAttachedOrCreate(SkillAttachments.SKILL_CONTAINER, SkillContainerImpl::new);

        if (skillContainer.getSource() != (Object) this) {
            skillContainer.setSource(tome$livingEntity());
        }

        return skillContainer;
    }

    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public SkillCooldownManager getSkillCooldownManager() {
        SkillCooldownManagerImpl skillCooldownManager =
                (SkillCooldownManagerImpl) getAttachedOrCreate(SkillAttachments.SKILL_COOLDOWN_MANAGER, SkillCooldownManagerImpl::new);

        if (skillCooldownManager.getSource() != (Object) this) {
            skillCooldownManager.setSource(tome$livingEntity());
        }

        return skillCooldownManager;
    }

    @SuppressWarnings({"AddedMixinMembersNamePattern", "UnstableApiUsage"})
    @Override
    public SkillManager getSkillManager() {
        SkillManagerImpl skillManager =
                (SkillManagerImpl) getAttachedOrCreate(SkillAttachments.SKILL_MANAGER, SkillManagerImpl::new);

        if (skillManager.getSource() != (Object) this) {
            skillManager.setSource(tome$livingEntity());
        }

        return skillManager;
    }

    @Inject(method = "onDeath", at = @At(value = "TAIL"))
    private void tome$onDeath(DamageSource damageSource, CallbackInfo ci) {
        if (getEntityWorld().isClient()) {
            return;
        }

        SkillManager skillManager = getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Inject(method = "onRemove", at = @At(value = "TAIL"))
    private void tome$onRemove(RemovalReason reason, CallbackInfo ci) {
        if (getEntityWorld().isClient()) {
            return;
        }

        SkillManager skillManager = getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tome$tick(CallbackInfo ci) {
        if (getEntityWorld().isClient()) {
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
