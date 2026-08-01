package net.threecrows.drehmal_archipelago.init;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.World;
import net.threecrows.drehmal_archipelago.APMod;

public class APDamageTypes {
    public static final RegistryKey<DamageType> DEATHLINK = register("deathlink");
    public static final RegistryKey<DamageType> BORDER_FAILSAFE = register("border_failsafe");

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(key));
    }

    private static RegistryKey<DamageType> register(String name) {
        return RegistryKey.of(RegistryKeys.DAMAGE_TYPE, APMod.id(name));
    }
}
