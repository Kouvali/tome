package org.kouv.tome.impl.channeling.manager;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.channeling.Channeling;
import org.kouv.tome.api.channeling.ChannelingContext;
import org.kouv.tome.api.channeling.manager.ChannelingManager;
import org.kouv.tome.impl.channeling.ChannelingContextImpl;

import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class ChannelingManagerImpl implements ChannelingManager {
    private final WeakReference<? extends LivingEntity> sourceRef;

    private @Nullable ChannelingContext<?> context = null;
    private Status status = Status.IDLE;

    public ChannelingManagerImpl(LivingEntity source) {
        this.sourceRef = new WeakReference<>(Objects.requireNonNull(source));
    }

    @Override
    public boolean isChanneling() {
        return status != Status.IDLE;
    }

    @Override
    public <S> boolean startChanneling(Channeling<S> channeling, S state) {
        Objects.requireNonNull(channeling);
        Objects.requireNonNull(state);
        if (status != Status.IDLE) {
            return false;
        }

        status = Status.STARTING;
        context = createContext(channeling, state);
        try {
            executeBehavior(context, it -> it.getChanneling().getStartBehavior().execute(it));
            status = Status.ACTIVE;
            return true;
        } catch (Exception exception) {
            context = null;
            status = Status.IDLE;
            throw exception;
        }
    }

    @Override
    public boolean completeChanneling() {
        if (status != Status.ACTIVE) {
            return false;
        }

        status = Status.COMPLETING;
        try {
            executeBehavior(context, it -> it.getChanneling().getCompleteBehavior().execute(it));
            return true;
        } finally {
            endChanneling();
        }
    }

    @Override
    public boolean cancelChanneling() {
        if (status != Status.ACTIVE) {
            return false;
        }

        if (!testCondition(context, it -> it.getChanneling().getCancelCondition().test(it))) {
            return false;
        }

        status = Status.CANCELLING;
        try {
            executeBehavior(context, it -> it.getChanneling().getCancelBehavior().execute(it));
            return true;
        } finally {
            endChanneling();
        }
    }

    @Override
    public boolean interruptChanneling() {
        if (status != Status.ACTIVE) {
            return false;
        }

        if (!testCondition(context, it -> it.getChanneling().getInterruptCondition().test(it))) {
            return false;
        }

        status = Status.INTERRUPTING;
        try {
            executeBehavior(context, it -> it.getChanneling().getInterruptBehavior().execute(it));
            return true;
        } finally {
            endChanneling();
        }
    }

    private void tickChanneling() {
        if (status == Status.ACTIVE) {
            executeBehavior(context, it -> it.getChanneling().getTickBehavior().execute(it));
        }
    }

    private void endChanneling() {
        if (status == Status.IDLE || status == Status.ENDING) {
            return;
        }

        status = Status.ENDING;
        try {
            executeBehavior(context, it -> it.getChanneling().getEndBehavior().execute(it));
        } finally {
            context = null;
            status = Status.IDLE;
        }
    }

    public void update() {
        tickChanneling();
    }

    public void discard() {
        endChanneling();
    }

    private <S> void executeBehavior(
            ChannelingContext<S> context,
            Consumer<? super ChannelingContext<S>> consumer
    ) {
        consumer.accept(context);
    }

    private <S> boolean testCondition(
            ChannelingContext<S> context,
            Predicate<? super ChannelingContext<S>> predicate
    ) {
        return predicate.test(context);
    }

    private <S> ChannelingContext<S> createContext(Channeling<S> channeling, S state) {
        return new ChannelingContextImpl<>(channeling, state, getSource());
    }

    private LivingEntity getSource() {
        return Optional.ofNullable(sourceRef.get())
                .orElseThrow(() -> new IllegalStateException("Entity is no longer available"));
    }

    private enum Status {
        IDLE,
        STARTING,
        ACTIVE,
        COMPLETING,
        CANCELLING,
        INTERRUPTING,
        ENDING
    }
}
