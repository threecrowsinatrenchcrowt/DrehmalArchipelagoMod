package net.threecrows.drehmal_archipelago.events.client;

import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.threecrows.drehmal_archipelago.common.regions.RegionBorderRenderer;

public class APWorldRenderEvent {
    public static void register() {
        WorldRenderEvents.AFTER_TRANSLUCENT.register(RegionBorderRenderer::render);
    }
}
