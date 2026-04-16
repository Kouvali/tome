package org.kouv.tome.api.buff;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.manager.BuffManager;

@ApiStatus.Experimental
public interface BuffContext<P> {
    Holder<? extends Buff<P>> getBuff();

    LivingEntity getTarget();

    P getParams();

    default BuffManager getBuffManager() {
        return getTarget().getBuffManager();
    }
}
