package org.kouv.tome.impl.skill.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.impl.skill.SkillContextImpl;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;

public final class SkillContainerImpl implements SkillContainer {
    @SuppressWarnings("unchecked")
    public static final Codec<SkillContainer> CODEC = RecordCodecBuilder.<SkillContainerImpl>create(instance ->
            instance.group(
                            SkillRegistries.SKILL.getEntryCodec()
                                    .listOf()
                                    .fieldOf("skills")
                                    .forGetter(container ->
                                            List.copyOf(
                                                    (Set<RegistryEntry<Skill<?>>>) (Set<?>) container.skills
                                            )
                                    )
                    )
                    .apply(instance, SkillContainerImpl::new)
    ).xmap(
            container -> container,
            container -> (SkillContainerImpl) container
    );

    private final Set<RegistryEntry<? extends Skill<?>>> skills;
    private @Nullable LivingEntity source = null;

    private SkillContainerImpl(
            Collection<? extends RegistryEntry<? extends Skill<?>>> skills
    ) {
        this.skills = new CopyOnWriteArraySet<>(Objects.requireNonNull(skills));
    }

    public SkillContainerImpl() {
        this(Set.of());
    }

    @Override
    public Set<? extends RegistryEntry<? extends Skill<?>>> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    @Override
    public boolean hasSkill(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return skills.contains(skill);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addSkill(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        boolean added = skills.add(skill);
        if (added) {
            handleSkillAdded((RegistryEntry<? extends Skill<Object>>) skill);
        }

        return added;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeSkill(RegistryEntry<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        boolean removed = skills.remove(skill);
        if (removed) {
            handleSkillRemoved((RegistryEntry<? extends Skill<Object>>) skill);
        }

        return removed;
    }

    public @Nullable LivingEntity getSource() {
        return source;
    }

    public void setSource(LivingEntity source) {
        this.source = Objects.requireNonNull(source);
    }

    @SuppressWarnings("unchecked")
    public void notifyEntityLoaded() {
        for (RegistryEntry<? extends Skill<?>> skill : skills) {
            handleEntityLoaded((RegistryEntry<? extends Skill<Object>>) skill);
        }
    }

    @SuppressWarnings("unchecked")
    public void notifyEntityUnloading() {
        for (RegistryEntry<? extends Skill<?>> skill : skills) {
            handleEntityUnloading((RegistryEntry<? extends Skill<Object>>) skill);
        }
    }

    private <S> void handleSkillAdded(RegistryEntry<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);
        skill.value().getAddedCallback().handle(context);
        SkillEvents.ADDED.invoker().onAdded(context);
    }

    private <S> void handleSkillRemoved(RegistryEntry<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);
        skill.value().getRemovedCallback().handle(context);
        SkillEvents.REMOVED.invoker().onRemoved(context);
    }

    private <S> void handleEntityLoaded(RegistryEntry<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);
        skill.value().getEntityLoadedCallback().handle(context);
    }

    private <S> void handleEntityUnloading(RegistryEntry<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);
        skill.value().getEntityUnloadingCallback().handle(context);
    }

    private <S> SkillContext<S> createContext(RegistryEntry<? extends Skill<S>> skill) {
        return new SkillContextImpl<>(skill, getSourceOrThrow());
    }

    private LivingEntity getSourceOrThrow() {
        return Optional.ofNullable(source)
                .orElseThrow(() -> new IllegalStateException("Source entity is not set"));
    }
}
