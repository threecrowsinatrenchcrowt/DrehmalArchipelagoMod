package net.threecrows.drehmal_archipelago.mixin.compat.jei;

import mezz.jei.library.render.ItemStackRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;
import net.threecrows.drehmal_archipelago.client.ArchipelagoItemIconRenderer;
import net.threecrows.drehmal_archipelago.util.tracker.ArchipelagoTrackingData;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemStackRenderer.class)
public class ItemStackRendererMixin {
    @Inject(method = "render(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/item/ItemStack;II)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawItemWithoutEntity(Lnet/minecraft/item/ItemStack;II)V"))
    private void archiepelago$render(DrawContext guiGraphics, ItemStack ingredient, int posX, int posY, CallbackInfo ci) {
        if (ArchipelagoTrackingData.UNCHECKED_ITEMS.contains(ingredient.getItem())) {
            ArchipelagoItemIconRenderer.renderIconHandledScreen(guiGraphics.getMatrices(), guiGraphics.getVertexConsumers(), posX, posY);
        }
    }
}
