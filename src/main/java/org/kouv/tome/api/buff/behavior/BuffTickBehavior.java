package org.kouv.tome.api.buff.behavior;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.BuffInstance;

import java.util.Objects;

@ApiStatus.Experimental
@FunctionalInterface
public interface BuffTickBehavior<P> {
    static <P> BuffTickBehavior<P> noOp() {
        return _ -> {};
    }

    void execute(BuffInstance<? extends P> instance);

    @ApiStatus.NonExtendable
    default BuffTickBehavior<P> andThen(BuffTickBehavior<? super P> after) {
        Objects.requireNonNull(after);
        return instance -> {
            execute(instance);
            after.execute(instance);
        };
    }
}
