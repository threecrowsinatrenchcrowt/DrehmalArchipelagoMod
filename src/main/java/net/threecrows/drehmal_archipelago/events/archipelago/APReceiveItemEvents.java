package net.threecrows.drehmal_archipelago.events.archipelago;

import io.github.archipelagomw.events.ArchipelagoEventListener;
import io.github.archipelagomw.events.ReceiveItemEvent;
import net.threecrows.drehmal_archipelago.APMod;
import net.threecrows.drehmal_archipelago.archipelago.items.ArchipelagoItems;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.util.APServerUtil;

public class APReceiveItemEvents {

    @ArchipelagoEventListener
    public void receiveItem(ReceiveItemEvent event) {
        APServerUtil.runOnServer(server -> {
            AbstractAPItem item = ArchipelagoItems.ITEMS.get(event.getItemName());
            APPersistentState states = APPersistentState.get();
            long index = event.getIndex();

            if (item != null) {
                if (!states.getReceivedItems().containsKey(index)) {
                    item.receiveItem(event.getItem(), server, index);
                    states.putItemIndex(index, event.getItemName());
                }
            }
        });
    }
}
