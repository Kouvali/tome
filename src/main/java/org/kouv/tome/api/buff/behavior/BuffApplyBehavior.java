package org.kouv.tome.api.buff.behavior;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.BuffInstance;

import java.util.Objects;

@ApiStatus.Experimental
@FunctionalInterface
public interface BuffApplyBehavior<P> {
    static <P> BuffApplyBehavior<P> noOp() {
        return instance -> {};
    }

    void execute(BuffInstance<? extends P> instance);

    @ApiStatus.NonExtendable
    default BuffApplyBehavior<P> andThen(BuffApplyBehavior<? super P> after) {
        Objects.requireNonNull(after);
        return instance -> {
            execute(instance);
            after.execute(instance);
        };
    }
}
