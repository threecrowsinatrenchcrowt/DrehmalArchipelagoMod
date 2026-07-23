package net.threecrows.drehmal_archipelago.archipelago.items.type.progression;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;

public class ServerCommandAPItem extends AbstractAPItem {
    private final String command;

    public ServerCommandAPItem(String command) {
        this.command = command;
    }

    @Override
    public void applyReward(ServerPlayerEntity player) {
        MinecraftServer server = player.getServer();
        if (server == null) return;

        CommandManager commandManager = server.getCommandManager();
        ServerCommandSource source = player.getCommandSource().withLevel(4);
        commandManager.executeWithPrefix(source, this.command);
    }
}
