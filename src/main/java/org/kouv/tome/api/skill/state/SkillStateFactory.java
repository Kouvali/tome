package org.kouv.tome.api.skill.state;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.skill.SkillContext;

import java.util.Objects;
import java.util.function.BiFunction;

@FunctionalInterface
public interface SkillStateFactory<S> {
    static <S> SkillStateFactory<S> constant(S state) {
        Objects.requireNonNull(state);
        return context -> SkillStateCreationResult.ok(state);
    }

    SkillStateCreationResult<S> create(SkillContext<?> context);

    @ApiStatus.NonExtendable
    default <R> SkillStateFactory<R> map(
            BiFunction<? super SkillContext<?>, ? super S, ? extends R> mapper
    ) {
        Objects.requireNonNull(mapper);
        return context -> create(context).map(state -> mapper.apply(context, state));
    }

    @ApiStatus.NonExtendable
    default <R> SkillStateFactory<R> flatMap(
            BiFunction<? super SkillContext<?>, ? super S, ? extends SkillStateCreationResult<R>> mapper
    ) {
        Objects.requireNonNull(mapper);
        return context -> create(context).flatMap(state -> mapper.apply(context, state));
    }
}
