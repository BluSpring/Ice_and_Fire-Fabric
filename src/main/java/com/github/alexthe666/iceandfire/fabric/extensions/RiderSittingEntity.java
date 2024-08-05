package com.github.alexthe666.iceandfire.fabric.extensions;

public interface RiderSittingEntity {
    default boolean shouldRiderSit() {
        return true;
    }
}
