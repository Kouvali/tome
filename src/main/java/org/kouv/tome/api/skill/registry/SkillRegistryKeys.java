package org.kouv.tome.api.skill.registry;

import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.Skill;

public interface SkillRegistryKeys {
    RegistryKey<Registry<Skill<?>>> SKILL = RegistryKey.ofRegistry(Identifier.of("tome", "skill"));

    @ApiStatus.Internal
    static void initialize() {
    }
}
