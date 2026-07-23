package net.threecrows.drehmal_archipelago.networking.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.APMod;
import net.threecrows.drehmal_archipelago.client.screens.LiteratureTrapScreen;

public class LiteratureTrapS2CPacket {
    public static final Identifier ID = APMod.id("literature_trap");

    public static void send(ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, ID, PacketByteBufs.create());
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            client.execute(() -> {
                if (client.player != null) {
                    client.setScreen(new LiteratureTrapScreen(Text.literal("Literature Trap"), client.player));
                }
            });
        }
    }
}
