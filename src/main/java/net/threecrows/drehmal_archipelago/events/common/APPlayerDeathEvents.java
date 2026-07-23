package net.threecrows.drehmal_archipelago.events.common;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.threecrows.drehmal_archipelago.util.mixinterfaces.IPlayerReceivedItems;

public class APPlayerDeathEvents {
    public static void register() {
        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            IPlayerReceivedItems oldIDs = (IPlayerReceivedItems) oldPlayer;
            IPlayerReceivedItems newIDs = (IPlayerReceivedItems) newPlayer;
            newIDs.archipelago$setItemIDs(oldIDs.archipelago$getItemIDs());
        });
    }
}
