package com.github.alexthe666.iceandfire.entity.props;

import com.github.alexthe666.citadel.server.message.CitadelPacket;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public record SyncEntityData(int entityId, CompoundTag tag) implements CitadelPacket {
    public void encode(final FriendlyByteBuf buffer) {
        buffer.writeInt(entityId);
        buffer.writeNbt(tag);
    }

    public static SyncEntityData decode(final FriendlyByteBuf buffer) {
        return new SyncEntityData(buffer.readInt(), buffer.readNbt());
    }

    public static void handle(final SyncEntityData message, Player player, BlockableEventLoop<?> loop) {
        if (player.level().isClientSide()) {
            loop.execute(() -> {
                Player localPlayer = CapabilityHandler.getLocalPlayer();

                if (localPlayer != null) {
                    EntityDataProvider.getCapability(localPlayer.level().getEntity(message.entityId)).ifPresent(data -> data.deserialize(message.tag));
                }
            });
        }
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        handle(this, player, server);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        handle(this, client.player, client);
    }
}
