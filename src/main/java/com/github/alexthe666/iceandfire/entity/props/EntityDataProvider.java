package com.github.alexthe666.iceandfire.entity.props;

import io.github.fabricators_of_create.porting_lib.core.util.INBTSerializable;
import io.github.fabricators_of_create.porting_lib.util.LazyOptional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public class EntityDataProvider implements INBTSerializable<CompoundTag> {
    public static final Map<Integer, LazyOptional<EntityData>> SERVER_CACHE = new HashMap<>();
    public static final Map<Integer, LazyOptional<EntityData>> CLIENT_CACHE = new HashMap<>();

    private final EntityData data = new EntityData();
    private final LazyOptional<EntityData> instance = LazyOptional.of(() -> data);

    @Override
    public void deserializeNBT(final CompoundTag tag) {
        instance.orElseThrow(() -> new IllegalArgumentException("Capability instance was not present")).deserialize(tag);
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.orElseThrow(() -> new IllegalArgumentException("Capability instance was not present")).serialize();
    }

    public static LazyOptional<EntityData> getCapability(final Entity entity) {
        if (entity instanceof LivingEntity) {
            int key = entity.getId();

            Map<Integer, LazyOptional<EntityData>> sidedCache = entity.level().isClientSide() ? CLIENT_CACHE : SERVER_CACHE;
            LazyOptional<EntityData> capability = sidedCache.get(key);

            if (capability == null) {
                capability = LazyOptional.ofObject(entity.getAttachedOrCreate(CapabilityHandler.ENTITY_DATA_CAPABILITY));
                sidedCache.put(key, capability);
            }

            return capability;
        }

        return LazyOptional.empty();
    }

    public static void removeCachedEntry(final Entity entity) {
        if (entity instanceof LivingEntity) {
            int key = entity.getId();

            if (entity.level().isClientSide()) {
                if (entity == CapabilityHandler.getLocalPlayer()) {
                    // Can trigger on death or when player leaves the game (this is when we want to actually clear)
                    CLIENT_CACHE.clear();
                } else {
                    CLIENT_CACHE.remove(key);
                }
            } else {
                SERVER_CACHE.remove(key);
            }
        }
    }
}
