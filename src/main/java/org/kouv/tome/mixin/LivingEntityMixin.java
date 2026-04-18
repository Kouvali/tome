package org.kouv.tome.mixin;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.kouv.tome.api.buff.attachment.BuffAttachments;
import org.kouv.tome.api.buff.entity.BuffEntity;
import org.kouv.tome.api.buff.manager.BuffManager;
import org.kouv.tome.api.skill.attachment.SkillAttachments;
import org.kouv.tome.api.skill.entity.SkillEntity;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.kouv.tome.impl.buff.manager.BuffManagerImpl;
import org.kouv.tome.impl.skill.manager.SkillContainerImpl;
import org.kouv.tome.impl.skill.manager.SkillCooldownManagerImpl;
import org.kouv.tome.impl.skill.manager.SkillManagerImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements BuffEntity, SkillEntity {
    private LivingEntityMixin(EntityType<?> type, Level world) {
        super(type, world);
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public BuffManager getBuffManager() {
        BuffManagerImpl buffManager =
                (BuffManagerImpl) getAttachedOrCreate(BuffAttachments.BUFF_MANAGER, BuffManagerImpl::new);

        if (buffManager.getTarget() != (Object) this) {
            buffManager.setTarget(tome$livingEntity());
        }

        return buffManager;
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public SkillContainer getSkillContainer() {
        SkillContainerImpl skillContainer =
                (SkillContainerImpl) getAttachedOrCreate(SkillAttachments.SKILL_CONTAINER, SkillContainerImpl::new);

        if (skillContainer.getSource() != (Object) this) {
            skillContainer.setSource(tome$livingEntity());
        }

        return skillContainer;
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public SkillCooldownManager getSkillCooldownManager() {
        SkillCooldownManagerImpl skillCooldownManager =
                (SkillCooldownManagerImpl) getAttachedOrCreate(SkillAttachments.SKILL_COOLDOWN_MANAGER, SkillCooldownManagerImpl::new);

        if (skillCooldownManager.getSource() != (Object) this) {
            skillCooldownManager.setSource(tome$livingEntity());
        }

        return skillCooldownManager;
    }

    @SuppressWarnings("AddedMixinMembersNamePattern")
    @Override
    public SkillManager getSkillManager() {
        SkillManagerImpl skillManager =
                (SkillManagerImpl) getAttachedOrCreate(SkillAttachments.SKILL_MANAGER, SkillManagerImpl::new);

        if (skillManager.getSource() != (Object) this) {
            skillManager.setSource(tome$livingEntity());
        }

        return skillManager;
    }

    @Inject(method = "die", at = @At(value = "TAIL"))
    private void tome$onDeath(DamageSource source, CallbackInfo ci) {
        if (level().isClientSide()) {
            return;
        }

        BuffManager buffManager = getBuffManager();
        buffManager.clearBuffs();

        SkillManager skillManager = getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Inject(method = "onRemoval", at = @At(value = "TAIL"))
    private void tome$onRemoval(RemovalReason reason, CallbackInfo ci) {
        if (level().isClientSide()) {
            return;
        }

        BuffManager buffManager = getBuffManager();
        buffManager.clearBuffs();

        SkillManager skillManager = getSkillManager();
        if (!skillManager.interruptCasting()) {
            skillManager.terminateCasting();
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    private void tome$onFirstTick(CallbackInfo ci) {
        if (level().isClientSide()) {
            return;
        }

        if (firstTick) {
            ((SkillContainerImpl) getSkillContainer()).refresh();
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    private void tome$onTick(CallbackInfo ci) {
        if (level().isClientSide()) {
            return;
        }

        ((BuffManagerImpl) getBuffManager()).update();
        ((SkillCooldownManagerImpl) getSkillCooldownManager()).update();
        ((SkillManagerImpl) getSkillManager()).update();
    }

    @Unique
    private LivingEntity tome$livingEntity() {
        return (LivingEntity) (Object) this;
    }
}
