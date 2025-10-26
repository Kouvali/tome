package org.kouv.tome.api.channeling.entity;

import org.kouv.tome.api.channeling.manager.ChannelingManager;

public interface ChannelingEntity {
    default ChannelingManager getChannelingManager() {
        throw new UnsupportedOperationException("Implemented via mixin");
    }
}
