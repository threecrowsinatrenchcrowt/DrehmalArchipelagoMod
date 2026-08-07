package net.threecrows.drehmal_archipelago.events.common;

import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.archipelago.ArchipelagoServerConnector;
import net.threecrows.drehmal_archipelago.networking.s2c.RegionBordersS2CPacket;


public class APServerPlayConnectionEvents {
    public static boolean syncData = false;

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            if (Archipelago.archipelago != null && !Archipelago.archipelago.isConnected()) {
                ArchipelagoServerConnector.connectToServer();
            }
            // sets this to true to use elsewhere since players can't be modified here!
            syncData = true;
        });
        
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            RegionBordersS2CPacket.send(handler.getPlayer());
        });
    }
}
