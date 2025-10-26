package org.kouv.tome.impl.skill.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillCooldownManagerImpl implements SkillCooldownManager {
    public static final Codec<SkillCooldownManager> CODEC = RecordCodecBuilder.<SkillCooldownManagerImpl>create(instance ->
            instance.group(
                            Codec.unboundedMap(Identifier.CODEC, Codec.INT)
                                    .fieldOf("cooldowns")
                                    .forGetter(cooldownManager ->
                                            Map.copyOf(
                                                    cooldownManager.cooldowns
                                            )
                                    )
                    )
                    .apply(instance, SkillCooldownManagerImpl::new)
    ).xmap(
            cooldownManager -> cooldownManager,
            cooldownManager -> (SkillCooldownManagerImpl) cooldownManager
    );

    private final Map<Identifier, Integer> cooldowns;

    private SkillCooldownManagerImpl(
            Map<? extends Identifier, ? extends Integer> cooldowns
    ) {
        this.cooldowns = new ConcurrentHashMap<>(Objects.requireNonNull(cooldowns));
    }

    public SkillCooldownManagerImpl() {
        this(Map.of());
    }

    @Override
    public boolean isCoolingDown(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return isCoolingDown(getId(skill));
    }

    @Override
    public boolean isCoolingDown(Identifier id) {
        Objects.requireNonNull(id);
        return getCooldown(id) > 0;
    }

    @Override
    public int getCooldown(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return getCooldown(getId(skill));
    }

    @Override
    public int getCooldown(Identifier id) {
        Objects.requireNonNull(id);
        return cooldowns.getOrDefault(id, 0);
    }

    @Override
    public void setCooldown(RegistryEntry<? extends Skill> skill, int cooldown) {
        Objects.requireNonNull(skill);
        setCooldown(getId(skill), cooldown);
    }

    @Override
    public void setCooldown(Identifier id, int cooldown) {
        Objects.requireNonNull(id);
        if (cooldown > 0) {
            cooldowns.put(id, cooldown);
        } else {
            cooldowns.remove(id);
        }
    }

    public void update() {
        Iterator<Map.Entry<Identifier, Integer>> iterator = cooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<Identifier, Integer> entry = iterator.next();
            if (entry.getValue() > 0) {
                entry.setValue(entry.getValue() - 1);
            } else {
                iterator.remove();
            }
        }
    }

    private Identifier getId(RegistryEntry<? extends Skill> skill) {
        return skill.getKey().map(RegistryKey::getValue)
                .orElseThrow();
    }
}
