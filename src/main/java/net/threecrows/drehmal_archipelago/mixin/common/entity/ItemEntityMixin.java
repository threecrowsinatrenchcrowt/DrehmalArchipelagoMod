package net.threecrows.drehmal_archipelago.mixin.common.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow private int itemAge;

    @Shadow public abstract ItemStack getStack();

    private boolean hasCustomNbt() {
        return this.getStack().hasNbt();
    }
    
    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    private void preventDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (hasCustomNbt()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void preventDespawn(CallbackInfo ci) {
        if (hasCustomNbt()) {
            this.itemAge = 0;
        }
    }
}