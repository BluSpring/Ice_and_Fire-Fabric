package com.github.alexthe666.iceandfire.entity.props;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.fabricators_of_create.porting_lib.entity.events.EntityEvents;
import io.github.fabricators_of_create.porting_lib.entity.events.LivingEntityEvents;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class CapabilityHandler {
    public static final ResourceLocation ENTITY_DATA = new ResourceLocation(IceAndFire.MODID, "entity_data");
    public static final AttachmentType<EntityData> ENTITY_DATA_CAPABILITY = AttachmentRegistry.<EntityData>builder()
        .persistent(
            RecordCodecBuilder
                .create(instance ->
                    instance.group(
                        CompoundTag.CODEC
                            .fieldOf("data")
                            .forGetter(EntityData::serialize)
                    )
                        .apply(instance, nbt -> {
                            var data = new EntityData();
                            data.deserialize(nbt);
                            return data;
                        })
                )
        )
        .initializer(EntityData::new)
        .buildAndRegister(ENTITY_DATA);

    public static void init() {
        EntityEvents.ON_JOIN_WORLD.register((entity, world, loadedFromDisk) -> {
            syncEntityData(entity);
            return true;
        });

        ServerEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            EntityDataProvider.removeCachedEntry(entity);
        });

        EntityTrackingEvents.START_TRACKING.register((trackedEntity, serverPlayer) -> {
            if (trackedEntity instanceof LivingEntity target) {
                EntityDataProvider.getCapability(target).ifPresent(data -> IceAndFire.sendMSGToPlayer(new SyncEntityData(target.getId(), data.serialize()), serverPlayer));
            }
        });
    }

    /* Currently there is no data which would need to be kept after death
    @SubscribeEvent
    public static void handleDeath(final PlayerEvent.Clone event) {
        if (event.isWasDeath()) { // TODO :: entering the end portal also triggers this but with this flag set as false
            Player oldPlayer = event.getOriginal();
            Player newPlayer = event.getEntity();

            oldPlayer.reviveCaps();

            // Unsure but at this point the cached entry might already be removed, no need to re-add it (since it will not be removed again)
            oldPlayer.getCapability(ENTITY_DATA_CAPABILITY).ifPresent(oldData -> {
                EntityDataProvider.getCapability(newPlayer).ifPresent(newData -> {
                    newData.deserialize(oldData.serialize());
                    // Sync to client is handled by 'EntityJoinLevelEvent'
                });
            });

            oldPlayer.invalidateCaps();
        }
    }
    */

    static {
        LivingEntityEvents.LivingTickEvent.TICK.register(CapabilityHandler::tickData);
    }

    public static void tickData(final LivingEntityEvents.LivingTickEvent event) {
        EntityDataProvider.getCapability(event.getEntity()).ifPresent(data -> data.tick(event.getEntity()));
    }

    public static void syncEntityData(final Entity entity) {
        if (entity.level().isClientSide() || !(entity instanceof LivingEntity)) {
            return;
        }

        if (entity instanceof ServerPlayer serverPlayer) {
            EntityDataProvider.getCapability(entity).ifPresent(data -> IceAndFire.NETWORK_WRAPPER.sendToClientsTrackingAndSelf(new SyncEntityData(entity.getId(), data.serialize()), serverPlayer));
        } else {
            EntityDataProvider.getCapability(entity).ifPresent(data -> IceAndFire.NETWORK_WRAPPER.sendToClientsTracking(new SyncEntityData(entity.getId(), data.serialize()), entity));
        }
    }

    public static @Nullable Player getLocalPlayer() {
        return IceAndFire.PROXY.getClientSidePlayer();
    }
}
