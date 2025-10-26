package org.kouv.tome.impl.skill.manager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.manager.SkillContainer;
import org.kouv.tome.api.skill.registry.SkillRegistries;

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
                                                    (Set<RegistryEntry<Skill>>) (Set<?>) container.skills
                                            )
                                    )
                    )
                    .apply(instance, SkillContainerImpl::new)
    ).xmap(
            container -> container,
            container -> (SkillContainerImpl) container
    );

    private final Set<RegistryEntry<? extends Skill>> skills;

    private SkillContainerImpl(
            Collection<? extends RegistryEntry<? extends Skill>> skills
    ) {
        this.skills = new CopyOnWriteArraySet<>(Objects.requireNonNull(skills));
    }

    public SkillContainerImpl() {
        this(Set.of());
    }

    @Override
    public Set<? extends RegistryEntry<? extends Skill>> getSkills() {
        return Collections.unmodifiableSet(skills);
    }

    @Override
    public boolean hasSkill(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return skills.contains(skill);
    }

    @Override
    public boolean addSkill(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return skills.add(skill);
    }

    @Override
    public boolean removeSkill(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return skills.remove(skill);
    }
}
