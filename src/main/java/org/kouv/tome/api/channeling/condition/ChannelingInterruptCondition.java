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

    boolean test(ChannelingContext<? extends S> context);

    default ChannelingInterruptCondition<S> and(ChannelingInterruptCondition<? super S> other) {
        Objects.requireNonNull(other);
        return context -> test(context) && other.test(context);
    }

    default ChannelingInterruptCondition<S> or(ChannelingInterruptCondition<? super S> other) {
        Objects.requireNonNull(other);
        return context -> test(context) || other.test(context);
    }
}
