package net.threecrows.drehmal_archipelago.events.common;

import io.github.archipelagomw.Client;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.archipelago.items.dataloader.APItemDataLoader;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.networking.s2c.RegionBordersS2CPacket;
import net.threecrows.drehmal_archipelago.util.APServerUtil;
import net.threecrows.drehmal_archipelago.util.tracker.ArchipelagoTrackingData;

public class APServerWorldEvents {
    public static void register() {
        ServerWorldEvents.LOAD.register(APServerWorldEvents::onLoad);
        ServerWorldEvents.UNLOAD.register(APServerWorldEvents::onUnload);
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            RegionBordersS2CPacket.send(player);
        });
    }

    private static void onLoad(MinecraftServer server, ServerWorld serverWorld) {
        APServerUtil.server = server;
        Archipelago.archipelago = new Archipelago();
        APPersistentState.get().addMissingChecks();
        // Since ItemTags aren't able to be checked when loading the item data initially, it's loaded here
        APItemDataLoader.processItemTags();
    }

    private static void onUnload(MinecraftServer server, ServerWorld serverWorld) {
        Archipelago.run(Client::close);
        APServerUtil.server = null;
        Archipelago.slotData = null;
        ArchipelagoTrackingData.clear();
        // Unloads Datapack stuffs so that the values don't carry over to the next world
        APItemDataLoader.unload();
    }
}
