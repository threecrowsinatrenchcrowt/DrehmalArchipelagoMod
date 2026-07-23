package net.threecrows.drehmal_archipelago.init.client;

import net.fabricmc.fabric.api.client.rendering.v1.CoreShaderRegistrationCallback;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.render.VertexFormats;
import net.threecrows.drehmal_archipelago.APMod;

public class APShaders {
    public static ShaderProgram greyscaleShader;

    public static void register() {
        CoreShaderRegistrationCallback.EVENT.register(context -> {
            context.register(
                    APMod.id("gui_greyscale"),
                    VertexFormats.POSITION_TEXTURE,
                    program -> greyscaleShader = program
            );
        });
    }
}
