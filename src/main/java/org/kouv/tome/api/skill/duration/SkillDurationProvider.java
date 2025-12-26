package org.kouv.tome.api.skill.duration;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;
import java.util.function.BiFunction;

public interface SkillDurationProvider {
    static SkillDurationProvider constant(int duration) {
        return context -> duration;
    }

    static SkillDurationProvider infinite() {
        return context -> -1;
    }

    static SkillDurationProvider instant() {
        return context -> 0;
    }

    int get(SkillContext<?> context);

    @ApiStatus.NonExtendable
    default SkillDurationProvider map(
            BiFunction<? super SkillContext<?>, ? super Integer, ? extends Integer> mapper
    ) {
        Objects.requireNonNull(mapper);
        return context -> mapper.apply(context, get(context));
    }
}
