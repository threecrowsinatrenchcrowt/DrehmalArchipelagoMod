package net.threecrows.drehmal_archipelago.events.archipelago;

import io.github.archipelagomw.events.ArchipelagoEventListener;
import io.github.archipelagomw.events.DeathLinkEvent;
import net.minecraft.text.Text;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.init.APDamageTypes;
import net.threecrows.drehmal_archipelago.util.APServerUtil;

public class APDeathlinkEvents {

    @ArchipelagoEventListener
    public static void onDeathlinkEvent(DeathLinkEvent event) {
        int deathlink = Archipelago.getFromSlot(mcSlotData -> mcSlotData.deathlink);
        if (deathlink != 0) {
            Archipelago.run(archipelago -> {
                Archipelago.lastDeathlinkPlayer = event.source;
                APServerUtil.runOnServer(server -> server.getPlayerManager().getPlayerList().forEach(player -> {
                    player.damage(APDamageTypes.of(player.getWorld(), APDamageTypes.DEATHLINK), Float.MAX_VALUE);
                }));
                APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.deathlinked.player", event.source));
                APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.deathlinked.cause", event.cause));
            });
        }
    }
}
