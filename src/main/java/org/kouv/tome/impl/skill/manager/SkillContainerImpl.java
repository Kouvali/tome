package org.kouv.tome.impl.skill.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.event.SkillEvents;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.registry.SkillRegistries;
import org.kouv.tome.impl.skill.SkillContextImpl;

import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;

public final class SkillContainerImpl implements SkillContainer {
    @SuppressWarnings("unchecked")
    public static final Codec<SkillContainer> CODEC = RecordCodecBuilder.<SkillContainerImpl>create(instance ->
            instance.group(
                            SkillRegistries.SKILL.holderByNameCodec()
                                    .listOf()
                                    .fieldOf("skills")
                                    .forGetter(container ->
                                            List.copyOf(
                                                    (Set<Holder<Skill<?>>>) (Set<?>) container.skills
                                            )
                                    )
                    )
                    .apply(instance, SkillContainerImpl::new)
    ).xmap(
            container -> container,
            container -> (SkillContainerImpl) container
    );

    private final Set<Holder<? extends Skill<?>>> skills;
    private @Nullable LivingEntity source = null;

    private SkillContainerImpl(
            Collection<? extends Holder<? extends Skill<?>>> skills
    ) {
        this.skills = new CopyOnWriteArraySet<>(Objects.requireNonNull(skills));
    }

    public SkillContainerImpl() {
        this(Set.of());
    }

    @Override
    public Set<? extends Holder<? extends Skill<?>>> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    @Override
    public boolean hasSkill(Holder<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        return skills.contains(skill);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean addSkill(Holder<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        boolean added = skills.add(skill);
        if (added) {
            handleSkillAdded((Holder<? extends Skill<Object>>) skill);
        }

        return added;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean removeSkill(Holder<? extends Skill<?>> skill) {
        Objects.requireNonNull(skill);
        boolean removed = skills.remove(skill);
        if (removed) {
            handleSkillRemoved((Holder<? extends Skill<Object>>) skill);
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
    public void refresh() {
        for (Holder<? extends Skill<?>> skill : skills) {
            handleSkillLoaded((Holder<? extends Skill<Object>>) skill);
        }
    }

    private <S> void handleSkillAdded(Holder<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);

        skill.value().getAttributeModifierSet().applyTemporaryModifiers(getSourceOrThrow().getAttributes());
        skill.value().getAddedCallback().handle(context);

        SkillEvents.ADDED.invoker().onAdded(context);
    }

    private <S> void handleSkillRemoved(Holder<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);

        skill.value().getAttributeModifierSet().removeModifiers(getSourceOrThrow().getAttributes());
        skill.value().getRemovedCallback().handle(context);

        SkillEvents.REMOVED.invoker().onRemoved(context);
    }

    private <S> void handleSkillLoaded(Holder<? extends Skill<S>> skill) {
        SkillContext<S> context = createContext(skill);

        skill.value().getAttributeModifierSet().applyTemporaryModifiers(getSourceOrThrow().getAttributes());
        skill.value().getLoadedCallback().handle(context);

        SkillEvents.LOADED.invoker().onLoaded(context);
    }

    private <S> SkillContext<S> createContext(Holder<? extends Skill<S>> skill) {
        return new SkillContextImpl<>(skill, getSourceOrThrow());
    }

    private LivingEntity getSourceOrThrow() {
        return Optional.ofNullable(source)
                .orElseThrow(() -> new IllegalStateException("Source entity is not set"));
    }
}
