package com.github.alexthe666.iceandfire.api.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

/**
 * GenericGriefEvent is fired right before an entity destroys or modifies blocks in some aspect. <br>
 * {@link #targetX} x coordinate being targeted for modification. <br>
 * {@link #targetY} y coordinate being targeted for modification. <br>
 * {@link #targetZ} z coordinate being targeted for modification. <br>
 * <br>
 * If this event is canceled, no block destruction or explosion will follow.<br>
 * <br>
 * This event does not have a result.
 * <br>
 * If you only want to deal with the damage caused by dragon fire, see {@link DragonFireDamageWorldEvent} <br>
 * <br>
 * false - cancel
 **/
public class GenericGriefEvent {
    public static final Event<GenericGriefCallback> EVENT = EventFactory.createArrayBacked(GenericGriefCallback.class, callbacks -> event -> {
        for (GenericGriefCallback callback : callbacks) {
            if (!callback.onGrief(event))
                return true;
        }

        return false;
    });

    private final LivingEntity entity;
    private final double targetX;
    private final double targetY;
    private final double targetZ;

    public GenericGriefEvent(LivingEntity griefer, double targetX, double targetY, double targetZ) {
        this.entity = griefer;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public double getTargetX() {
        return targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public double getTargetZ() {
        return targetZ;
    }

    public interface GenericGriefCallback {
        boolean onGrief(GenericGriefEvent event);
    }
}
