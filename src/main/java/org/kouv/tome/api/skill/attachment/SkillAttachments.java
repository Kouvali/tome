package org.kouv.tome.api.skill.attachment;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.kouv.tome.impl.skill.manager.SkillContainerImpl;
import org.kouv.tome.impl.skill.manager.SkillCooldownManagerImpl;

public interface SkillAttachments {
    @SuppressWarnings("UnstableApiUsage")
    AttachmentType<SkillContainer> SKILL_CONTAINER = AttachmentRegistry
            .create(
                    Identifier.of("tome", "skill_container"),
                    builder -> builder
                            .persistent(SkillContainerImpl.CODEC)
                            .copyOnDeath()
            );

    @SuppressWarnings("UnstableApiUsage")
    AttachmentType<SkillCooldownManager> SKILL_COOLDOWN_MANAGER = AttachmentRegistry
            .create(
                    Identifier.of("tome", "skill_cooldown_manager"),
                    builder -> builder
                            .persistent(SkillCooldownManagerImpl.CODEC)
                            .copyOnDeath()
            );

    @SuppressWarnings("UnstableApiUsage")
    AttachmentType<SkillManager> SKILL_MANAGER = AttachmentRegistry
            .create(
                    Identifier.of("tome", "skill_manager")
            );

    @ApiStatus.Internal
    static void initialize() {
    }
}
