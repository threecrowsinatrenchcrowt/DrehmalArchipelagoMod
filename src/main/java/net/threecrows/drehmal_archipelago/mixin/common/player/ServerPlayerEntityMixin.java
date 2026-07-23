package net.threecrows.drehmal_archipelago.mixin.common.player;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;

import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.GameRules;
import net.threecrows.drehmal_archipelago.init.APDamageTypes;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin {
    @WrapOperation(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/GameRules;getBoolean(Lnet/minecraft/world/GameRules$Key;)Z", ordinal = 0))
    private boolean archipelago$onDeath(GameRules instance, GameRules.Key<GameRules.BooleanRule> rule, Operation<Boolean> original, @Local(argsOnly = true) DamageSource damageSource) {
        if (damageSource.isOf(APDamageTypes.DEATHLINK)) {
            return false;
        }
        return original.call(instance, rule);
    }
}
