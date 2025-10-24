package org.kouv.tome.api.channeling.behavior;

import org.kouv.tome.api.channeling.ChannelingContext;

import java.util.Objects;

@FunctionalInterface
public interface ChannelingStartBehavior<S> {
    static <S> ChannelingStartBehavior<S> noOp() {
        return context -> {};
    }

    void execute(ChannelingContext<S> context);

    default ChannelingStartBehavior<S> andThen(ChannelingStartBehavior<S> after) {
        Objects.requireNonNull(after);
        return context -> {
            execute(context);
            after.execute(context);
        };
    }
}
