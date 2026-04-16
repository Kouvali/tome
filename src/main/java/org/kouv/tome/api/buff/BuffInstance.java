package org.kouv.tome.api.buff;

import org.jetbrains.annotations.ApiStatus;
import org.kouv.tome.api.entity.attribute.AttributeModifierTracker;

@ApiStatus.Experimental
public interface BuffInstance<P> extends BuffContext<P> {
    void setParams(P params);

    AttributeModifierTracker getAttributeModifierTracker();

    int getDuration();

    void setDuration(int duration);

    boolean isMarkedForExpiration();

    void setMarkedForExpiration(boolean markedForExpiration);

    int getElapsedTime();

    void setElapsedTime(int elapsedTime);

    default int getRemainingDuration() {
        int duration = getDuration();
        return duration < 0 ?
                -1 :
                Math.max(0, duration - getElapsedTime());
    }

    default float getProgress() {
        int duration = getDuration();
        return duration <= 0 ?
                0.0f :
                Math.clamp((float) getElapsedTime() / duration, 0.0f, 1.0f);
    }
}
