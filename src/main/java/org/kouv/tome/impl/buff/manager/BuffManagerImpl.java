package org.kouv.tome.impl.buff.manager;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.buff.Buff;
import org.kouv.tome.api.buff.BuffContext;
import org.kouv.tome.api.buff.BuffInstance;
import org.kouv.tome.api.buff.event.BuffTestCallback;
import org.kouv.tome.api.buff.manager.BuffManager;
import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;
import org.kouv.tome.impl.buff.BuffContextImpl;
import org.kouv.tome.impl.buff.BuffInstanceImpl;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.IntUnaryOperator;
import java.util.function.UnaryOperator;

public final class BuffManagerImpl implements BuffManager {
    private final Map<Holder<? extends Buff<?>>, BuffInstance<?>> instances = new ConcurrentHashMap<>();
    private @Nullable LivingEntity target = null;

    @Override
    public Collection<? extends BuffInstance<?>> getBuffInstances() {
        return Collections.unmodifiableCollection(instances.values());
    }

    @Override
    public boolean hasBuff(Holder<? extends Buff<?>> buff) {
        Objects.requireNonNull(buff);
        return instances.containsKey(buff);
    }

    @SuppressWarnings("unchecked")
    @Override
    public @Nullable <P> BuffInstance<P> getBuffInstance(Holder<? extends Buff<P>> buff) {
        Objects.requireNonNull(buff);
        return (BuffInstance<P>) instances.get(buff);
    }

    @Override
    public <P> boolean applyBuff(
            Holder<? extends Buff<P>> buff,
            P params,
            int duration
    ) {
        Objects.requireNonNull(buff);
        Objects.requireNonNull(params);
        if (hasBuff(buff)) {
            return false;
        }

        BuffContext<P> context = createContext(buff, params);
        if (!BuffTestCallback.EVENT.invoker().onTest(context)) {
            return false;
        }

        executeApply(createInstance(context, duration));
        return true;
    }

    @Override
    public <P> boolean updateBuff(
            Holder<? extends Buff<P>> buff,
            UnaryOperator<P> paramsMapper,
            IntUnaryOperator durationMapper
    ) {
        Objects.requireNonNull(buff);
        Objects.requireNonNull(paramsMapper);
        Objects.requireNonNull(durationMapper);
        BuffInstance<P> instance = getBuffInstance(buff);
        if (instance == null) {
            return false;
        }

        executeUpdate(
                instance,
                paramsMapper.apply(instance.getParams()),
                durationMapper.applyAsInt(instance.getDuration())
        );
        return true;
    }

    @Override
    public boolean removeBuff(Holder<? extends Buff<?>> buff) {
        Objects.requireNonNull(buff);
        BuffInstance<?> instance = instances.get(buff);
        if (instance == null) {
            return false;
        }

        executeRemove(instance);
        return true;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clearBuffs() {
        for (Holder<? extends Buff<?>> buff : instances.keySet().toArray(Holder[]::new)) {
            removeBuff(buff);
        }
    }

    public @Nullable LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = Objects.requireNonNull(target);
    }

    @SuppressWarnings("unchecked")
    public void update() {
        for (BuffInstance<?> instance : instances.values().toArray(BuffInstance[]::new)) {
            executeTick(
                    (BuffInstance<Object>) instance
            );
        }
    }

    private <P> void executeApply(BuffInstance<P> instance) {
        instances.put(instance.getBuff(), instance);
        instance.getBuff().value().getApplyBehavior().execute(instance);

        if (hasBuff(instance.getBuff()) && isReadyToExpire(instance)) {
            executeExpire(instance);
        }
    }

    private <P> void executeUpdate(BuffInstance<P> instance, P params, int duration) {
        instance.setParams(params);
        instance.setDuration(duration);
        instance.getBuff().value().getUpdateBehavior().execute(instance);

        if (hasBuff(instance.getBuff()) && isReadyToExpire(instance)) {
            executeExpire(instance);
        }
    }

    private <P> void executeExpire(BuffInstance<P> instance) {
        instance.getBuff().value().getExpireBehavior().execute(instance);
        executeEnd(instance);
    }

    private <P> void executeRemove(BuffInstance<P> instance) {
        instance.getBuff().value().getRemoveBehavior().execute(instance);
        executeEnd(instance);
    }

    private <P> void executeEnd(BuffInstance<P> instance) {
        instance.getBuff().value().getEndBehavior().execute(instance);
        instance.getAttributeModifierTracker().clearModifiers();
        instances.remove(instance.getBuff());
    }

    private <P> void executeTick(BuffInstance<P> instance) {
        ((BuffInstanceImpl<?>) instance).update();
        instance.getBuff().value().getTickBehavior().execute(instance);

        if (hasBuff(instance.getBuff()) && isReadyToExpire(instance)) {
            executeExpire(instance);
        }
    }

    private boolean isReadyToExpire(BuffInstance<?> instance) {
        return instance.isMarkedForExpiration() ||
                (instance.getDuration() >= 0 &&
                        instance.getDuration() <= instance.getElapsedTime());
    }

    private <P> BuffContext<P> createContext(Holder<? extends Buff<P>> buff, P params) {
        return new BuffContextImpl<>(
                buff,
                getTargetOrThrow(),
                params
        );
    }

    private <P> BuffInstance<P> createInstance(BuffContext<P> context, int duration) {
        return new BuffInstanceImpl<>(
                context.getBuff(),
                context.getTarget(),
                context.getParams(),
                new AttributeModifierTracker(context.getTarget().getAttributes()),
                duration
        );
    }

    private LivingEntity getTargetOrThrow() {
        return Optional.ofNullable(target)
                .orElseThrow(() -> new IllegalStateException("Target entity is not set"));
    }
}
