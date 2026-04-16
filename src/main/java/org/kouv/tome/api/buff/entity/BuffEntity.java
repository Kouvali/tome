package org.kouv.tome.api.buff.entity;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.buff.manager.BuffManager;

@ApiStatus.Experimental
public interface BuffEntity {
    default BuffManager getBuffManager() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
}
