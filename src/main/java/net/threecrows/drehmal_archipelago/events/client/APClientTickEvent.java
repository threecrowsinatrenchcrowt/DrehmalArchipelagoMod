package net.threecrows.drehmal_archipelago.events.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.threecrows.drehmal_archipelago.APModConfigs;
import net.threecrows.drehmal_archipelago.client.screens.ItemTrackerScreen;
import net.threecrows.drehmal_archipelago.init.client.APKeybindings;

public class APClientTickEvent {
    private static boolean pressedTrackerOpenKeybinding = false;

    public static boolean trackerTipSent = false;
    public static int trackerTipTimer = 0;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            if (client.player != null) {
                if (!APModConfigs.Main.disableTrackerTooltip) {
                    if (!trackerTipSent) {
                        if (trackerTipTimer++ > 150) {
                            client.player.sendMessage(
                                    Text.translatable("drehmal_archipelago.tip").setStyle(Style.EMPTY.withColor(Formatting.DARK_GREEN).withBold(true))
                                            .append(Text.translatable("drehmal_archipelago.tip.tracker",
                                                    APKeybindings.GAME_TRACKER_KEYBINDING.getBoundKeyLocalizedText().getString()
                                            ).setStyle(Style.EMPTY.withColor(Formatting.YELLOW).withBold(false)))
                            );
                            trackerTipSent = true;
                        }
                    }
                }
            }

            //if (APKeybindings.GAME_TRACKER_KEYBINDING.isPressed()) {
            //    if (!pressedTrackerOpenKeybinding) {
            //        client.setScreen(new ItemTrackerScreen(Text.literal("Game Tracker")));
            //        pressedTrackerOpenKeybinding = true;
            //    }
            //} else {
            //    pressedTrackerOpenKeybinding = false;
            //}
        });
    }
}
