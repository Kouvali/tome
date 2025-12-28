package org.kouv.tome.api.skill.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.Skill;

public interface SkillRegistryKeys {
    ResourceKey<Registry<Skill<?>>> SKILL = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath("tome", "skill"));

    @ApiStatus.Internal
    static void initialize() {
    }
}
