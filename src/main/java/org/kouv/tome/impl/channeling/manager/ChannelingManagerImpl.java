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

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <S> ChannelingContext<S> getChannelingContext(Channeling<S> channeling) {
        Objects.requireNonNull(channeling);
        return context == null || !context.getChanneling().equals(channeling) ?
                null :
                (ChannelingContext<S>) context;
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <S> ChannelingContext<S> getChannelingContext(Class<S> clazz) {
        Objects.requireNonNull(clazz);
        return context == null || !clazz.isInstance(context.getState()) ?
                null :
                (ChannelingContext<S>) context;
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
            executeStartBehavior(context);
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

        assert context != null;
        status = Status.COMPLETING;
        try {
            executeCompleteBehavior(context);
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

        assert context != null;
        if (!testCancelCondition(context)) {
            return false;
        }

        status = Status.CANCELLING;
        try {
            executeCancelBehavior(context);
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

        assert context != null;
        if (!testInterruptCondition(context)) {
            return false;
        }

        status = Status.INTERRUPTING;
        try {
            executeInterruptBehavior(context);
            return true;
        } finally {
            endChanneling();
        }
    }

    private void tickChanneling() {
        if (status != Status.ACTIVE) {
            return;
        }

        assert context != null;
        executeTickBehavior(context);
    }

    private void endChanneling() {
        if (status == Status.IDLE || status == Status.ENDING) {
            return;
        }

        assert context != null;
        status = Status.ENDING;
        try {
            executeEndBehavior(context);
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

    private <S> void executeCancelBehavior(ChannelingContext<S> context) {
        context.getChanneling()
                .getCancelBehavior().execute(context);
    }

    private <S> void executeCompleteBehavior(ChannelingContext<S> context) {
        context.getChanneling()
                .getCompleteBehavior().execute(context);
    }

    private <S> void executeEndBehavior(ChannelingContext<S> context) {
        context.getChanneling()
                .getEndBehavior().execute(context);
    }

    private <S> void executeInterruptBehavior(ChannelingContext<S> context) {
        context.getChanneling()
                .getInterruptBehavior().execute(context);
    }

    private <S> void executeStartBehavior(ChannelingContext<S> context) {
        context.getChanneling()
                .getStartBehavior().execute(context);
    }

    private <S> void executeTickBehavior(ChannelingContext<S> context) {
        context.getChanneling()
                .getTickBehavior().execute(context);
    }

    private <S> boolean testCancelCondition(ChannelingContext<S> context) {
        return context.getChanneling()
                .getCancelCondition().test(context);
    }

    private <S> boolean testInterruptCondition(ChannelingContext<S> context) {
        return context.getChanneling()
                .getInterruptCondition().test(context);
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
