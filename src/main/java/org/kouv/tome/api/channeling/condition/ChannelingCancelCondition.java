package org.kouv.tome.api.channeling.condition;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingCancelCondition<S> {
    static <S> ChannelingCancelCondition<S> alwaysAllow() {
        return context -> true;
    }

    static <S> ChannelingCancelCondition<S> alwaysDeny() {
        return context -> false;
    }

    boolean test(ChannelingContext<? extends S> context);

    default ChannelingCancelCondition<S> and(ChannelingCancelCondition<? super S> other) {
        Objects.requireNonNull(other);
        return context -> test(context) && other.test(context);
    }

    default ChannelingCancelCondition<S> or(ChannelingCancelCondition<? super S> other) {
        Objects.requireNonNull(other);
        return context -> test(context) || other.test(context);
    }
}
