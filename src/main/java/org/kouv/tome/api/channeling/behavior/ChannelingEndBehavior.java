package org.kouv.tome.api.channeling.behavior;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingEndBehavior<S> {
    static <S> ChannelingEndBehavior<S> noOp() {
        return context -> {};
    }

    void execute(ChannelingContext<? extends S> context);

    default ChannelingEndBehavior<S> andThen(ChannelingEndBehavior<? super S> after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
