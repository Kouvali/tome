package org.kouv.tome.api.channeling.condition;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingInterruptCondition<S> {
    static <S> ChannelingInterruptCondition<S> alwaysAllow() {
        return context -> true;
    }

    static <S> ChannelingInterruptCondition<S> alwaysDeny() {
        return context -> false;
    }

    boolean test(ChannelingContext<S> context);

    default ChannelingInterruptCondition<S> and(ChannelingInterruptCondition<S> other) {
        Objects.requireNonNull(other);
        return context -> test(context) && other.test(context);
    }

    default ChannelingInterruptCondition<S> or(ChannelingInterruptCondition<S> other) {
        Objects.requireNonNull(other);
        return context -> test(context) || other.test(context);
    }
}
