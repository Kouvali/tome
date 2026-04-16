package org.kouv.tome.api.buff;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;
import org.kouv.tome.api.buff.behavior.*;
import org.kouv.tome.api.buff.registry.BuffRegistries;

import java.util.Objects;

@ApiStatus.Experimental
public final class Buff<P> {
    private final DataComponentMap components;
    private final BuffApplyBehavior<P> applyBehavior;
    private final BuffEndBehavior<P> endBehavior;
    private final BuffExpireBehavior<P> expireBehavior;
    private final BuffRemoveBehavior<P> removeBehavior;
    private final BuffTickBehavior<P> tickBehavior;
    private final BuffUpdateBehavior<P> updateBehavior;

    private @Nullable Identifier id;
    private @Nullable String translationKey;
    private @Nullable Component name;

    private Buff(
            DataComponentMap components,
            BuffApplyBehavior<P> applyBehavior,
            BuffEndBehavior<P> endBehavior,
            BuffExpireBehavior<P> expireBehavior,
            BuffRemoveBehavior<P> removeBehavior,
            BuffTickBehavior<P> tickBehavior,
            BuffUpdateBehavior<P> updateBehavior
    ) {
        this.components = Objects.requireNonNull(components);
        this.applyBehavior = Objects.requireNonNull(applyBehavior);
        this.endBehavior = Objects.requireNonNull(endBehavior);
        this.expireBehavior = Objects.requireNonNull(expireBehavior);
        this.removeBehavior = Objects.requireNonNull(removeBehavior);
        this.tickBehavior = Objects.requireNonNull(tickBehavior);
        this.updateBehavior = Objects.requireNonNull(updateBehavior);
    }

    public static <P> Builder<P> builder() {
        return new Builder<>();
    }

    public DataComponentMap getComponents() {
        return components;
    }

    public BuffApplyBehavior<P> getApplyBehavior() {
        return applyBehavior;
    }

    public BuffEndBehavior<P> getEndBehavior() {
        return endBehavior;
    }

    public BuffExpireBehavior<P> getExpireBehavior() {
        return expireBehavior;
    }

    public BuffRemoveBehavior<P> getRemoveBehavior() {
        return removeBehavior;
    }

    public BuffTickBehavior<P> getTickBehavior() {
        return tickBehavior;
    }

    public BuffUpdateBehavior<P> getUpdateBehavior() {
        return updateBehavior;
    }

    public Identifier getId() {
        if (id == null) {
            id = BuffRegistries.BUFF.getKey(this);
        }

        return id;
    }

    public String getTranslationKey() {
        if (translationKey == null) {
            translationKey = Util.makeDescriptionId("buff", getId());
        }

        return translationKey;
    }

    public Component getName() {
        if (name == null) {
            name = Component.translatable(getTranslationKey());
        }

        return name;
    }

    public static final class Builder<P> {
        private DataComponentMap components = DataComponentMap.EMPTY;
        private BuffApplyBehavior<P> applyBehavior = BuffApplyBehavior.noOp();
        private BuffEndBehavior<P> endBehavior = BuffEndBehavior.noOp();
        private BuffExpireBehavior<P> expireBehavior = BuffExpireBehavior.noOp();
        private BuffRemoveBehavior<P> removeBehavior = BuffRemoveBehavior.noOp();
        private BuffTickBehavior<P> tickBehavior = BuffTickBehavior.noOp();
        private BuffUpdateBehavior<P> updateBehavior = BuffUpdateBehavior.noOp();

        private Builder() {
        }

        public DataComponentMap getComponents() {
            return components;
        }

        public Builder<P> setComponents(DataComponentMap components) {
            this.components = Objects.requireNonNull(components);
            return this;
        }

        public BuffApplyBehavior<P> getApplyBehavior() {
            return applyBehavior;
        }

        public Builder<P> setApplyBehavior(BuffApplyBehavior<P> applyBehavior) {
            this.applyBehavior = Objects.requireNonNull(applyBehavior);
            return this;
        }

        public BuffEndBehavior<P> getEndBehavior() {
            return endBehavior;
        }

        public Builder<P> setEndBehavior(BuffEndBehavior<P> endBehavior) {
            this.endBehavior = Objects.requireNonNull(endBehavior);
            return this;
        }

        public BuffExpireBehavior<P> getExpireBehavior() {
            return expireBehavior;
        }

        public Builder<P> setExpireBehavior(BuffExpireBehavior<P> expireBehavior) {
            this.expireBehavior = Objects.requireNonNull(expireBehavior);
            return this;
        }

        public BuffRemoveBehavior<P> getRemoveBehavior() {
            return removeBehavior;
        }

        public Builder<P> setRemoveBehavior(BuffRemoveBehavior<P> removeBehavior) {
            this.removeBehavior = Objects.requireNonNull(removeBehavior);
            return this;
        }

        public BuffTickBehavior<P> getTickBehavior() {
            return tickBehavior;
        }

        public Builder<P> setTickBehavior(BuffTickBehavior<P> tickBehavior) {
            this.tickBehavior = Objects.requireNonNull(tickBehavior);
            return this;
        }

        public BuffUpdateBehavior<P> getUpdateBehavior() {
            return updateBehavior;
        }

        public Builder<P> setUpdateBehavior(BuffUpdateBehavior<P> updateBehavior) {
            this.updateBehavior = Objects.requireNonNull(updateBehavior);
            return this;
        }

        public Buff<P> build() {
            return new Buff<>(
                    components,
                    applyBehavior,
                    endBehavior,
                    expireBehavior,
                    removeBehavior,
                    tickBehavior,
                    updateBehavior
            );
        }
    }
}
