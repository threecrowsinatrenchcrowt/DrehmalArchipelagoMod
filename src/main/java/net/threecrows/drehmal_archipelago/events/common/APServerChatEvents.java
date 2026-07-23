package net.threecrows.drehmal_archipelago.events.common;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.threecrows.drehmal_archipelago.APModConfigs;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.init.APAdvancements;
import net.threecrows.drehmal_archipelago.util.APServerUtil;

public class APServerChatEvents {
    public static void register() {
        // Sends Chat messages that Minecraft Players Send in Chat
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            if (sender != null && APModConfigs.Main.chatMessagesAppearInClient) {
                Archipelago.run(archipelago -> {
                    String msg = message.getContent().getString();

                    if (!msg.startsWith("!")) {
                        msg = "<" + sender.getName().getString() + "> " + msg;
                    }

                    archipelago.sendChat(msg);
                });
            }
        });
    }
}
