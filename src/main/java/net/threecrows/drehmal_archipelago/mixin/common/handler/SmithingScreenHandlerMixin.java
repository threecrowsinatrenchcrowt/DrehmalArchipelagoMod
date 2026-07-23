package net.threecrows.drehmal_archipelago.mixin.common.handler;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ForgingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.SmithingScreenHandler;
import net.threecrows.drehmal_archipelago.init.APTags;
import net.threecrows.drehmal_archipelago.util.APItemAccessUtil;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SmithingScreenHandler.class)
public abstract class SmithingScreenHandlerMixin extends ForgingScreenHandler {
    public SmithingScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(type, syncId, playerInventory, context);
    }
    /*

    This Mixin is used for preventing Smithing Table recipes from working when locked
        - This also works in the inventory due to the same method being called there

     */

    @WrapOperation(method = "updateResult", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isItemEnabled(Lnet/minecraft/resource/featuretoggle/FeatureSet;)Z"))
    private boolean archipelago$updateResult(ItemStack instance, FeatureSet enabledFeatures, Operation<Boolean> original) {
        if (instance.isIn(APTags.ARCHIPELAGO_LOCK_SMITHING) && !APItemAccessUtil.allowCraftOrUse(this.player, instance.getItem())) {
            return false;
        }
        return original.call(instance, enabledFeatures);
    }
}
