package org.kouv.tome.api.channeling;

import org.kouv.tome.api.channeling.behavior.*;
import org.kouv.tome.api.channeling.condition.ChannelingCancelCondition;
import org.kouv.tome.api.channeling.condition.ChannelingInterruptCondition;

import java.util.Objects;

public final class Channeling<S> {
    private final ChannelingCancelBehavior<S> cancelBehavior;
    private final ChannelingCompleteBehavior<S> completeBehavior;
    private final ChannelingEndBehavior<S> endBehavior;
    private final ChannelingInterruptBehavior<S> interruptBehavior;
    private final ChannelingStartBehavior<S> startBehavior;
    private final ChannelingTickBehavior<S> tickBehavior;
    private final ChannelingCancelCondition<S> cancelCondition;
    private final ChannelingInterruptCondition<S> interruptCondition;

    private Channeling(
            ChannelingCancelBehavior<S> cancelBehavior,
            ChannelingCompleteBehavior<S> completeBehavior,
            ChannelingEndBehavior<S> endBehavior,
            ChannelingInterruptBehavior<S> interruptBehavior,
            ChannelingStartBehavior<S> startBehavior,
            ChannelingTickBehavior<S> tickBehavior,
            ChannelingCancelCondition<S> cancelCondition,
            ChannelingInterruptCondition<S> interruptCondition
    ) {
        this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
        this.completeBehavior = Objects.requireNonNull(completeBehavior);
        this.endBehavior = Objects.requireNonNull(endBehavior);
        this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
        this.startBehavior = Objects.requireNonNull(startBehavior);
        this.tickBehavior = Objects.requireNonNull(tickBehavior);
        this.cancelCondition = Objects.requireNonNull(cancelCondition);
        this.interruptCondition = Objects.requireNonNull(interruptCondition);
    }

    public static <S> Builder<S> builder() {
        return new Builder<>();
    }

    public ChannelingCancelBehavior<S> getCancelBehavior() {
        return cancelBehavior;
    }

    public ChannelingCompleteBehavior<S> getCompleteBehavior() {
        return completeBehavior;
    }

    public ChannelingEndBehavior<S> getEndBehavior() {
        return endBehavior;
    }

    public ChannelingInterruptBehavior<S> getInterruptBehavior() {
        return interruptBehavior;
    }

    public ChannelingStartBehavior<S> getStartBehavior() {
        return startBehavior;
    }

    public ChannelingTickBehavior<S> getTickBehavior() {
        return tickBehavior;
    }

    public ChannelingCancelCondition<S> getCancelCondition() {
        return cancelCondition;
    }

    public ChannelingInterruptCondition<S> getInterruptCondition() {
        return interruptCondition;
    }

    public static final class Builder<S> {
        private ChannelingCancelBehavior<S> cancelBehavior = ChannelingCancelBehavior.noOp();
        private ChannelingCompleteBehavior<S> completeBehavior = ChannelingCompleteBehavior.noOp();
        private ChannelingEndBehavior<S> endBehavior = ChannelingEndBehavior.noOp();
        private ChannelingInterruptBehavior<S> interruptBehavior = ChannelingInterruptBehavior.noOp();
        private ChannelingStartBehavior<S> startBehavior = ChannelingStartBehavior.noOp();
        private ChannelingTickBehavior<S> tickBehavior = ChannelingTickBehavior.noOp();
        private ChannelingCancelCondition<S> cancelCondition = ChannelingCancelCondition.alwaysAllow();
        private ChannelingInterruptCondition<S> interruptCondition = ChannelingInterruptCondition.alwaysAllow();

        private Builder() {
        }

        public ChannelingCancelBehavior<S> getCancelBehavior() {
            return cancelBehavior;
        }

        public Builder<S> setCancelBehavior(ChannelingCancelBehavior<S> cancelBehavior) {
            this.cancelBehavior = Objects.requireNonNull(cancelBehavior);
            return this;
        }

        public ChannelingCompleteBehavior<S> getCompleteBehavior() {
            return completeBehavior;
        }

        public Builder<S> setCompleteBehavior(ChannelingCompleteBehavior<S> completeBehavior) {
            this.completeBehavior = Objects.requireNonNull(completeBehavior);
            return this;
        }

        public ChannelingEndBehavior<S> getEndBehavior() {
            return endBehavior;
        }

        public Builder<S> setEndBehavior(ChannelingEndBehavior<S> endBehavior) {
            this.endBehavior = Objects.requireNonNull(endBehavior);
            return this;
        }

        public ChannelingInterruptBehavior<S> getInterruptBehavior() {
            return interruptBehavior;
        }

        public Builder<S> setInterruptBehavior(ChannelingInterruptBehavior<S> interruptBehavior) {
            this.interruptBehavior = Objects.requireNonNull(interruptBehavior);
            return this;
        }

        public ChannelingStartBehavior<S> getStartBehavior() {
            return startBehavior;
        }

        public Builder<S> setStartBehavior(ChannelingStartBehavior<S> startBehavior) {
            this.startBehavior = Objects.requireNonNull(startBehavior);
            return this;
        }

        public ChannelingTickBehavior<S> getTickBehavior() {
            return tickBehavior;
        }

        public Builder<S> setTickBehavior(ChannelingTickBehavior<S> tickBehavior) {
            this.tickBehavior = Objects.requireNonNull(tickBehavior);
            return this;
        }

        public ChannelingCancelCondition<S> getCancelCondition() {
            return cancelCondition;
        }

        public Builder<S> setCancelCondition(ChannelingCancelCondition<S> cancelCondition) {
            this.cancelCondition = Objects.requireNonNull(cancelCondition);
            return this;
        }

        public ChannelingInterruptCondition<S> getInterruptCondition() {
            return interruptCondition;
        }

        public Builder<S> setInterruptCondition(ChannelingInterruptCondition<S> interruptCondition) {
            this.interruptCondition = Objects.requireNonNull(interruptCondition);
            return this;
        }

        public Channeling<S> build() {
            return new Channeling<>(
                    cancelBehavior,
                    completeBehavior,
                    endBehavior,
                    interruptBehavior,
                    startBehavior,
                    tickBehavior,
                    cancelCondition,
                    interruptCondition
            );
        }
    }
}
