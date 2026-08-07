package net.threecrows.drehmal_archipelago.networking.s2c;

import net.threecrows.drehmal_archipelago.APMod;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.archipelago.regions.Edge;
import net.threecrows.drehmal_archipelago.archipelago.regions.RegionManager;
import net.threecrows.drehmal_archipelago.archipelago.regions.Vec2d;
import net.threecrows.drehmal_archipelago.common.regions.BorderState;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class RegionBordersS2CPacket {
    public static final Identifier ID = APMod.id("region_borders");

    public static void send(ServerPlayerEntity player) {
        PacketByteBuf buf = PacketByteBufs.create();
        List<Edge> segments = RegionManager.ALL_EDGES;
        RegistryKey<World> dimension = player.getWorld().getRegistryKey();
        List<Edge> dimSegments = segments.stream()
            .filter(e -> e.getDimension().equals(dimension))
            .filter(e -> APPersistentState.get().getUnlockedRegionIds().contains(e.getRegionA()) ^ APPersistentState.get().getUnlockedRegionIds().contains(e.getRegionB()))
            .toList();
        boolean regionLocks = APPersistentState.get().getRegionLocks();
        if (!regionLocks) {
            dimSegments = new ArrayList<Edge>();
        }
        //APMod.LOGGER.info(dimSegments.get(0).getRegionA());
        buf.writeVarInt(dimSegments.size());
        for (Edge edge : dimSegments) {
            buf.writeDouble(edge.getStart().x());
            buf.writeDouble(edge.getStart().z());
            buf.writeDouble(edge.getEnd().x());
            buf.writeDouble(edge.getEnd().z());
            buf.writeString(edge.getRegionA());
            buf.writeString(edge.getRegionB());
            buf.writeIdentifier(edge.getDimension().getValue());
        }
        ServerPlayNetworking.send(player, ID, buf);
    }

    public static void sendToAll(MinecraftServer server) {
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            send(player);
        }
    }

    public static class Handler {
        public static void receive(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
            int count = buf.readVarInt();
            List<Edge> segments = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                segments.add(new Edge(
                    new Vec2d(buf.readDouble(), buf.readDouble()),
                    new Vec2d(buf.readDouble(), buf.readDouble()),
                    buf.readString(),
                    buf.readString(),
                    RegistryKey.of(RegistryKeys.WORLD, buf.readIdentifier())
                ));
            }
            // APMod.LOGGER.info(segments.toString());
            client.execute(() -> BorderState.setActiveSegments(segments));
        }
    }
}
