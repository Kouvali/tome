package org.kouv.tome.api.channeling.behavior;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingCompleteBehavior<S> {
    static <S> ChannelingCompleteBehavior<S> noOp() {
        return context -> {};
    }

    void execute(ChannelingContext<? extends S> context);

    default ChannelingCompleteBehavior<S> andThen(ChannelingCompleteBehavior<? super S> after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
