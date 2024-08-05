package com.github.alexthe666.iceandfire.message;

import com.github.alexthe666.citadel.server.message.CitadelPacket;
import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import me.pepperbell.simplenetworking.SimpleChannel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.util.thread.BlockableEventLoop;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class MessageSpawnParticleAt implements CitadelPacket {
    private double x;
    private double y;
    private double z;
    private int particleType;

    public MessageSpawnParticleAt() {
    }

    public MessageSpawnParticleAt(double x, double y, double z, int particleType) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.particleType = particleType;
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

    public static MessageSpawnParticleAt read(FriendlyByteBuf buf) {
        return new MessageSpawnParticleAt(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readInt());
    }

    public static void write(MessageSpawnParticleAt message, FriendlyByteBuf buf) {
        buf.writeDouble(message.x);
        buf.writeDouble(message.y);
        buf.writeDouble(message.z);
        buf.writeInt(message.particleType);
    }

    public static class Handler {
        public Handler() {
        }

        public static void handle(final MessageSpawnParticleAt message, Player player, BlockableEventLoop<?> loop) {
            loop.execute(() -> {
                if (player != null) {
                    ItemStack mainHand = player.getMainHandItem();

                    if (!mainHand.isEmpty() && mainHand.getItem() == IafItemRegistry.DRAGON_DEBUG_STICK.get()) {
                        player.level().addParticle(ParticleTypes.SMOKE, message.x, message.y, message.z, 0, 0, 0);
                    }
                }
            });
        }
    }
}
