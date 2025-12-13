package org.kouv.tome.impl.skill.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import org.kouv.tome.impl.skill.manager.SkillContainerImpl;

public final class SkillEventHandlers {
    public static void initialize() {
        ServerEntityEvents.ENTITY_LOAD.register(SkillEventHandlers::onLoad);
        ServerEntityEvents.ENTITY_UNLOAD.register(SkillEventHandlers::onUnload);
    }

    private static void onLoad(Entity entity, ServerWorld world) {
        if (entity instanceof LivingEntity livingEntity) {
            ((SkillContainerImpl) livingEntity.getSkillContainer()).notifyEntityLoaded();
        }
    }

    private static void onUnload(Entity entity, ServerWorld world) {
        if (entity instanceof LivingEntity livingEntity) {
            ((SkillContainerImpl) livingEntity.getSkillContainer()).notifyEntityUnloading();
        }
    }
}
