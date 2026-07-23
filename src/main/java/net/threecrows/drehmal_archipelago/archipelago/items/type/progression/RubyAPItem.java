package net.threecrows.drehmal_archipelago.archipelago.items.type.progression;

import io.github.archipelagomw.parts.NetworkItem;
import net.minecraft.server.MinecraftServer;
import net.minecraft.sound.SoundEvent;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.init.APSounds;

public class RubyAPItem extends AbstractAPItem {

    @Override
    protected SoundEvent getSoundEvent() {
        return APSounds.RUBY_RECEIVED;
    }

    @Override
    protected void triggerOneTimeEffect(NetworkItem item, MinecraftServer server) {
        APPersistentState state = APPersistentState.get();
        state.setCurrentRubyCount(state.getCollectedRubies() + 1);
    }
}
