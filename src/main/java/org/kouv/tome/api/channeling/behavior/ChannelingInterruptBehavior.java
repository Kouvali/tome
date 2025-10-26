package org.kouv.tome.api.channeling.behavior;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingInterruptBehavior<S> {
    static <S> ChannelingInterruptBehavior<S> noOp() {
        return context -> {};
    }

    void execute(ChannelingContext<? extends S> context);

    default ChannelingInterruptBehavior<S> andThen(ChannelingInterruptBehavior<? super S> after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
