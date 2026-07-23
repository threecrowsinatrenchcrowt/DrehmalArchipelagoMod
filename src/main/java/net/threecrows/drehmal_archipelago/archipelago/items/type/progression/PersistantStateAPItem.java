package net.threecrows.drehmal_archipelago.archipelago.items.type.progression;

import io.github.archipelagomw.parts.NetworkItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;

public class PersistantStateAPItem extends AbstractAPItem {
    private final String key;

    public PersistantStateAPItem(String key) {
        this.key = key;
    }

    @Override
    protected void triggerOneTimeEffect(NetworkItem item, MinecraftServer server) {
        APPersistentState.get().triggerCheck(this.key);
    }
}
