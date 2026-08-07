package net.threecrows.drehmal_archipelago.mixin.common.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.threecrows.drehmal_archipelago.util.mixinterfaces.TNTEntityDuck;

@Mixin(TntEntity.class)
public abstract class TNTEntityMixin implements TNTEntityDuck {

    @Unique
    private boolean drehmal_archipelago$noBlockDamage = false;

    @Override
    public void drehmal_archipelago$setNoBlockDamage(boolean value) {
        this.drehmal_archipelago$noBlockDamage = value;
    }

    @Override
    public boolean drehmal_archipelago$isNoBlockDamage() {
        return this.drehmal_archipelago$noBlockDamage;
    }

    @Redirect(
        method = "explode",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/World;createExplosion(Lnet/minecraft/entity/Entity;DDDFLnet/minecraft/world/World$ExplosionSourceType;)Lnet/minecraft/world/explosion/Explosion;"
        )
    )
    private Explosion drehmal_archipelago$redirectExplosion(World world, Entity entity, double x, double y, double z, float power, World.ExplosionSourceType type) {
        World.ExplosionSourceType actualType = this.drehmal_archipelago$noBlockDamage ? World.ExplosionSourceType.NONE : type;
        return world.createExplosion(entity, x, y, z, power, actualType);
    }
}