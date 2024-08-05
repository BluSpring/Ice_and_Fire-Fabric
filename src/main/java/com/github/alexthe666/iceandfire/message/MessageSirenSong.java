package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.citadel.server.message.CitadelPacket;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.entity.EntitySiren;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.Supplier;

public class MessageSirenSong implements CitadelPacket {

    public int sirenId;
    public boolean isSinging;

    public MessageSirenSong(int sirenId, boolean isSinging) {
        this.sirenId = sirenId;
        this.isSinging = isSinging;
    }

    public MessageSirenSong() {
    }

    @Override
    public void handle(MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl listener, PacketSender responseSender, SimpleChannel channel) {
        Handler.handle(this, player, server);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void handle(Minecraft client, ClientPacketListener listener, PacketSender responseSender, SimpleChannel channel) {
        Handler.handle(this, client.player, client);
    }

    @Override
    public void encode(FriendlyByteBuf buf) {
        write(this, buf);
    }

    public static MessageSirenSong read(FriendlyByteBuf buf) {
        return new MessageSirenSong(buf.readInt(), buf.readBoolean());
    }

    public static void write(MessageSirenSong message, FriendlyByteBuf buf) {
        buf.writeInt(message.sirenId);
        buf.writeBoolean(message.isSinging);
    }


    public static class Handler {
        public Handler() {
        }

        public static void handle(final MessageSirenSong message, Player player, BlockableEventLoop<?> loop) {
            loop.execute(() -> {
                if (player != null) {
                    Entity entity = player.level().getEntity(message.sirenId);

                    if (entity instanceof EntitySiren siren) {
                        siren.setSinging(message.isSinging);
                    }
                }
            });
        }
    }

}