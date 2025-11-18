package org.kouv.tome.impl.skill;

import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import org.kouv.tome.api.skill.Skill;
import org.kouv.tome.api.skill.SkillContext;
import org.kouv.tome.api.skill.SkillInstance;

import java.util.Objects;
import java.util.Optional;

public final class SkillInstanceImpl<S> implements SkillInstance<S> {
    private final SkillContext<S> context;
    private final S state;
    private final int startTime;

    public SkillInstanceImpl(SkillContext<S> context, S state) {
        this.context = Objects.requireNonNull(context);
        this.state = Objects.requireNonNull(state);
        this.startTime = getServer().getTicks();
    }

    @Override
    public S getState() {
        return state;
    }

    @Override
    public int getElapsedTime() {
        return getServer().getTicks() - startTime;
    }

    @Override
    public RegistryEntry<? extends Skill<S>> getSkill() {
        return context.getSkill();
    }

    @Override
    public LivingEntity getSource() {
        return context.getSource();
    }

    private MinecraftServer getServer() {
        return Optional.ofNullable(getSource().getEntityWorld().getServer())
                .orElseThrow(() -> new IllegalStateException("Server instance is not available"));
    }
}
