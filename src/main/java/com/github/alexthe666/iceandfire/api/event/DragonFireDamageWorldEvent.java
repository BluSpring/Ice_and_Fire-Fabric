package com.github.alexthe666.iceandfire.api.event;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * DragonFireDamageWorldEvent is fired right before a Dragon damages/changes terrain fire, lightning or ice. <br>
 * {@link #dragonBase} dragon in question. <br>
 * {@link #targetX} x coordinate being targeted for burning/freezing. <br>
 * {@link #targetY} y coordinate being targeted for burning/freezing. <br>
 * {@link #targetZ} z coordinate being targeted for burning/freezing. <br>
 * <br>
 * If this event is canceled, no blocks will be modified by the dragons breath.<br>
 * <br>
 * This event does not have a result.
 * <br>
 * <br>
 * If you want to cancel all aspects of dragon fire, see {@link DragonFireEvent} <br>
 * <br>
 * false - cancel
 **/
public class DragonFireDamageWorldEvent {
    public static final Event<DragonFireDamageWorldCallback> EVENT = EventFactory.createArrayBacked(DragonFireDamageWorldCallback.class, callbacks -> event -> {
        for (DragonFireDamageWorldCallback callback : callbacks) {
            if (!callback.onFireDamage(event))
                return true;
        }

        return false;
    });

    private EntityDragonBase dragonBase;
    private double targetX;
    private double targetY;
    private double targetZ;

    public DragonFireDamageWorldEvent(EntityDragonBase dragonBase, double targetX, double targetY, double targetZ) {
        this.dragonBase = dragonBase;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetZ = targetZ;
    }

    public EntityDragonBase getDragon() {
        return dragonBase;
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

    public interface DragonFireDamageWorldCallback {
        boolean onFireDamage(DragonFireDamageWorldEvent event);
    }
}
