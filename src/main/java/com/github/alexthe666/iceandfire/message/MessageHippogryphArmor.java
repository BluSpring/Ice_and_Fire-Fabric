package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.citadel.server.message.CitadelPacket;
import com.github.alexthe666.iceandfire.entity.EntityHippocampus;
import com.github.alexthe666.iceandfire.entity.EntityHippogryph;
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

public class MessageHippogryphArmor implements CitadelPacket {

    public int dragonId;
    public int slot_index;
    public int armor_type;

    public MessageHippogryphArmor(int dragonId, int slot_index, int armor_type) {
        this.dragonId = dragonId;
        this.slot_index = slot_index;
        this.armor_type = armor_type;
    }

    public MessageHippogryphArmor() {
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

    public static MessageHippogryphArmor read(FriendlyByteBuf buf) {
        return new MessageHippogryphArmor(buf.readInt(), buf.readInt(), buf.readInt());
    }

    public static void write(MessageHippogryphArmor message, FriendlyByteBuf buf) {
        buf.writeInt(message.dragonId);
        buf.writeInt(message.slot_index);
        buf.writeInt(message.armor_type);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(final MessageHippogryphArmor message, Player player, BlockableEventLoop<?> loop) {
            loop.execute(() -> {
                if (player != null) {
                    Entity entity = player.level().getEntity(message.dragonId);

                    if (entity instanceof EntityHippogryph hippogryph) {
                        if (message.slot_index == 0) {
                            hippogryph.setSaddled(message.armor_type == 1);
                        } else if (message.slot_index == 1) {
                            hippogryph.setChested(message.armor_type == 1);
                        } else if (message.slot_index == 2) {
                            hippogryph.setArmor(message.armor_type);
                        }
                    } else if (entity instanceof EntityHippocampus hippo) {
                        if (message.slot_index == 0) {
                            hippo.setSaddled(message.armor_type == 1);
                        } else if (message.slot_index == 1) {
                            hippo.setChested(message.armor_type == 1);
                        } else if (message.slot_index == 2) {
                            hippo.setArmor(message.armor_type);
                        }
                    }
                }
            });
        }
    }
}