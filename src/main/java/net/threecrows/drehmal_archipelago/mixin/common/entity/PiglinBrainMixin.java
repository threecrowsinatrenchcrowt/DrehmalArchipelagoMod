package net.threecrows.drehmal_archipelago.mixin.common.entity;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.entity.mob.PiglinBrain;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PiglinBrain.class)
public class PiglinBrainMixin {
    @ModifyReturnValue(method = "isGoldenItem", at = @At("RETURN"))
    private static boolean archipelago$isGoldenItem(boolean original) {
        if (!APPersistentState.get().getBooleanCheckValue("bartering")) {
            return false;
        }
        return original;
    }
    @ModifyReturnValue(method = "acceptsForBarter", at = @At("RETURN"))
    private static boolean archipelago$acceptsForBarter(boolean original) {
        if (!APPersistentState.get().getBooleanCheckValue("bartering")) {
            return false;
        }
        return original;
    }
}
