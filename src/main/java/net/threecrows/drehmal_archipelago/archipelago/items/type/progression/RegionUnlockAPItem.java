package net.threecrows.drehmal_archipelago.archipelago.items.type.progression;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;

public class RegionUnlockAPItem extends AbstractAPItem {
    private final String id;

    public RegionUnlockAPItem(String id) {
        this.id = id;
    }

    @Override
    public void applyReward(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        CommandManager commandManager = server.getCommandManager();
        ServerCommandSource source = player.getCommandSource().withLevel(4);
        commandManager.executeWithPrefix(source, "function terminus:unlock_tower/" + this.id + "_unlock");

        APPersistentState.get().unlockRegion(server, id);
    }
}
