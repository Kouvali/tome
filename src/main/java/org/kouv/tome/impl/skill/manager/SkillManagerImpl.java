package org.kouv.tome.impl.skill.manager;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillResponse;
import org.kouv.tome.api.skill.manager.SkillManager;
import org.kouv.tome.impl.skill.SkillContextImpl;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;

public final class SkillManagerImpl implements SkillManager {
    private final WeakReference<? extends LivingEntity> sourceRef;

    public SkillManagerImpl(LivingEntity source) {
        this.sourceRef = new WeakReference<>(Objects.requireNonNull(source));
    }

    @Override
    public SkillResponse testSkill(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return skill.value().getCondition().test(createContext(skill));
    }

    @Override
    public SkillResponse castSkill(RegistryEntry<? extends Skill> skill) {
        Objects.requireNonNull(skill);
        return testSkill(skill) instanceof SkillResponse.Failure failure ?
                failure :
                skill.value().getAction().execute(createContext(skill));
    }

    private SkillContext createContext(RegistryEntry<? extends Skill> skill) {
        return new SkillContextImpl(skill, getSource());
    }

    private LivingEntity getSource() {
        return Optional.ofNullable(sourceRef.get())
                .orElseThrow(() -> new IllegalStateException("Entity is no longer available"));
    }
}
