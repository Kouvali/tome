package org.kouv.tome.api.channeling.behavior;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingCancelBehavior<S> {
    static <S> ChannelingCancelBehavior<S> noOp() {
        return context -> {};
    }

    void execute(ChannelingContext<? extends S> context);

    default ChannelingCancelBehavior<S> andThen(ChannelingCancelBehavior<? super S> after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
