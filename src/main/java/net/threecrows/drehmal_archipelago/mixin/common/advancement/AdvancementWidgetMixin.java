package net.threecrows.drehmal_archipelago.mixin.common.advancement;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;


import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.advancement.Advancement;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.advancement.AdvancementWidget;
import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.util.APAdvancementHelper;

@Mixin(AdvancementWidget.class)
public abstract class AdvancementWidgetMixin {

    @Shadow @Final private Advancement advancement;

    @WrapOperation(
        method = "renderWidgets",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/DrawContext;drawTexture(Lnet/minecraft/util/Identifier;IIIIII)V"
        )
    )
    private void tintBackground(DrawContext context, Identifier texture, int x, int y,
                                 int u, int v, int width, int height, Operation<Void> original) {
        int color = APAdvancementHelper.advancementColor(this.advancement.getId());
        float a = ((color >> 24) & 0xFF) / 255f;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        RenderSystem.setShaderColor(r, g, b, a == 0 ? 1f : a);
        original.call(context, texture, x, y, u, v, width, height);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
    }
}