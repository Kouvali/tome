package org.kouv.tome.api.channeling.behavior;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingTickBehavior<S> {
    static <S> ChannelingTickBehavior<S> noOp() {
        return context -> {};
    }

    void execute(ChannelingContext<S> context);

    default ChannelingTickBehavior<S> andThen(ChannelingTickBehavior<S> after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
