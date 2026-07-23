package net.threecrows.drehmal_archipelago.init;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.APMod;

public class APSounds {

    public static final SoundEvent RUBY_RECEIVED = register("drehmal_archipelago.ruby.receive");

    public static SoundEvent register(String name) {
        Identifier id = APMod.id(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {}
}
