package net.threecrows.drehmal_archipelago.events.common;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.networking.s2c.SendArchipelagoInfoS2CPacket;
import net.threecrows.drehmal_archipelago.networking.s2c.UpdatePlayerAbilitiesS2CPacket;
import net.threecrows.drehmal_archipelago.util.APAdvancementHelper;

public class APServerTickEvents {

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            if (APServerPlayConnectionEvents.syncData) {
                APServerPlayConnectionEvents.syncData = false;
                APAdvancementHelper.resyncAdvancements();
                server.getPlayerManager().getPlayerList().forEach(player -> {
                    UpdatePlayerAbilitiesS2CPacket.send(player);
                    SendArchipelagoInfoS2CPacket.send(player);
                });
                AbstractAPItem.sync(server);
            }
        });
    }
}
