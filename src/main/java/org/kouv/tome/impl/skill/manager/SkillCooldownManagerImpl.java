package org.kouv.tome.impl.skill.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.registry.SkillRegistries;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class SkillCooldownManagerImpl implements SkillCooldownManager {
    @SuppressWarnings("unchecked")
    public static final Codec<SkillCooldownManager> CODEC = RecordCodecBuilder.<SkillCooldownManagerImpl>create(instance ->
            instance.group(
                            Codec.unboundedMap(SkillRegistries.SKILL.getEntryCodec(), Codec.INT)
                                    .fieldOf("cooldowns")
                                    .forGetter(cooldownManager ->
                                            Map.copyOf(
                                                    (Map<RegistryEntry<Skill<?>>, Integer>) (Map<?, ?>) cooldownManager.cooldowns
                                            )
                                    )
                    )
                    .apply(instance, SkillCooldownManagerImpl::new)
    ).xmap(
            cooldownManager -> cooldownManager,
            cooldownManager -> (SkillCooldownManagerImpl) cooldownManager
    );

    private final Map<RegistryEntry<? extends Skill<?>>, Integer> cooldowns;
    private @Nullable LivingEntity source = null;

    private SkillCooldownManagerImpl(
            Map<? extends RegistryEntry<? extends Skill<?>>, ? extends Integer> cooldowns
    ) {
        this.cooldowns = new ConcurrentHashMap<>(Objects.requireNonNull(cooldowns));
    }

    public SkillCooldownManagerImpl() {
        this(Map.of());
    }

    @Override
    public boolean isCoolingDown(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return getCooldown(skill) > 0;
    }

    @Override
    public int getCooldown(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return cooldowns.getOrDefault(skill, 0);
    }

    @Override
    public void setCooldown(RegistryEntry<? extends Skill<?>> skill, int cooldown) {
        Objects.requireNonNull(skill);
        if (cooldown > 0) {
            if (cooldowns.put(skill, cooldown) == null) {
                SkillEvents.COOLDOWN_STARTED.invoker().onCooldownStarted(getSourceOrThrow(), skill);
            }
        } else {
            if (cooldowns.remove(skill) != null) {
                SkillEvents.COOLDOWN_ENDED.invoker().onCooldownEnded(getSourceOrThrow(), skill);
            }
        }
    }

    public @Nullable LivingEntity getSource() {
        return source;
    }

    public void setSource(LivingEntity source) {
        this.source = Objects.requireNonNull(source);
    }

    public void update() {
        Iterator<Map.Entry<RegistryEntry<? extends Skill<?>>, Integer>> iterator = cooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RegistryEntry<? extends Skill<?>>, Integer> entry = iterator.next();
            if (entry.getValue() > 1) {
                entry.setValue(entry.getValue() - 1);
            } else {
                iterator.remove();
                SkillEvents.COOLDOWN_ENDED.invoker().onCooldownEnded(getSourceOrThrow(), entry.getKey());
            }
        }
    }

    private LivingEntity getSourceOrThrow() {
        return Optional.ofNullable(source)
                .orElseThrow(() -> new IllegalStateException("Source entity is not set"));
    }
}
