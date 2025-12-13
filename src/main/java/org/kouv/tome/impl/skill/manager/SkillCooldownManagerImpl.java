package org.kouv.tome.impl.skill.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.kouv.tome.api.skill.manager.SkillCooldownManager;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.impl.skill.SkillContextImpl;

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

    @SuppressWarnings("unchecked")
    @Override
    public void setCooldown(RegistryEntry<? extends Skill<?>> skill, int cooldown) {
        Objects.requireNonNull(skill);
        if (cooldown > 0) {
            if (cooldowns.put(skill, cooldown) == null) {
                handleCooldownStarted((RegistryEntry<? extends Skill<Object>>) skill);
            }
        } else {
            if (cooldowns.remove(skill) != null) {
                handleCooldownEnded((RegistryEntry<? extends Skill<Object>>) skill);
            }
        }
    }

    public @Nullable LivingEntity getSource() {
        return source;
    }

    public void setSource(LivingEntity source) {
        this.source = Objects.requireNonNull(source);
    }

    @SuppressWarnings("unchecked")
    public void update() {
        Iterator<Map.Entry<RegistryEntry<? extends Skill<?>>, Integer>> iterator = cooldowns.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<RegistryEntry<? extends Skill<?>>, Integer> entry = iterator.next();
            if (entry.getValue() > 1) {
                entry.setValue(entry.getValue() - 1);
            } else {
                iterator.remove();
                handleCooldownEnded((RegistryEntry<? extends Skill<Object>>) entry.getKey());
            }
        }
    }

    private <S> void handleCooldownStarted(RegistryEntry<? extends Skill<S>> skill) {
        SkillContext<?> context = createContext(skill);
        skill.value().getCooldownStartedCallback().handle(context);
        SkillEvents.COOLDOWN_STARTED.invoker().onCooldownStarted(context);
    }

    private <S> void handleCooldownEnded(RegistryEntry<? extends Skill<S>> skill) {
        SkillContext<?> context = createContext(skill);
        skill.value().getCooldownEndedCallback().handle(context);
        SkillEvents.COOLDOWN_ENDED.invoker().onCooldownEnded(context);
    }

    private <S> SkillContext<S> createContext(RegistryEntry<? extends Skill<S>> skill) {
        return new SkillContextImpl<>(skill, getSourceOrThrow());
    }

    private LivingEntity getSourceOrThrow() {
        return Optional.ofNullable(source)
                .orElseThrow(() -> new IllegalStateException("Source entity is not set"));
    }
}
