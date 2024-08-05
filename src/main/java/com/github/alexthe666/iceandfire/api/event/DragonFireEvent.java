package com.github.alexthe666.iceandfire.api.event;

import com.github.alexthe666.iceandfire.entity.EntityDragonBase;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

/**
 * DragonFireEvent is fired right before a Dragon breathes fire or ice. <br>
 * {@link #dragonBase} dragon in question. <br>
 * {@link #targetX} x coordinate being targeted for burning/freezing. <br>
 * {@link #targetY} y coordinate being targeted for burning/freezing. <br>
 * {@link #targetZ} z coordinate being targeted for burning/freezing. <br>
 * <br>
 * If this event is canceled, no fire will be spawned from the dragon's mouth.<br>
 * <br>
 * <br>
 * <br>
 * If you only want to deal with the damage caused by dragon fire, see {@link DragonFireDamageWorldEvent} <br>
 * <br>
 * false - cancel
 **/
public class DragonFireEvent {
    private EntityDragonBase dragonBase;
    private double targetX;
    private double targetY;
    private double targetZ;

    public static final Event<DragonFireCallback> EVENT = EventFactory.createArrayBacked(DragonFireCallback.class, callbacks -> event -> {
        for (DragonFireCallback callback : callbacks) {
            if (!callback.onDragonFire(event))
                return true;
        }

        return false;
    });

    public DragonFireEvent(EntityDragonBase dragonBase, double targetX, double targetY, double targetZ) {
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

    public interface DragonFireCallback {
        boolean onDragonFire(DragonFireEvent event);
    }
}
