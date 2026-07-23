package net.threecrows.drehmal_archipelago.archipelago;
import io.github.archipelagomw.Client;
import io.github.archipelagomw.events.ConnectionResultEvent;
import io.github.archipelagomw.flags.ItemsHandling;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.threecrows.drehmal_archipelago.events.archipelago.*;
import net.threecrows.drehmal_archipelago.util.APServerUtil;

import org.jetbrains.annotations.Nullable;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

public class Archipelago extends Client {
    public static Archipelago archipelago;
    public static @Nullable MCSlotData slotData;
    public static String lastDeathlinkPlayer = "Unknown";

    public Archipelago() {
        super();
        this.setGame("Drehmal");
        this.setItemsHandlingFlags(ItemsHandling.SEND_ITEMS + ItemsHandling.SEND_OWN_ITEMS + ItemsHandling.SEND_STARTING_INVENTORY);

        this.getEventManager().registerListener(new APPrintJsonEvents());
        this.getEventManager().registerListener(new APReceiveItemEvents());
        this.getEventManager().registerListener(new APConnectEvents());
        this.getEventManager().registerListener(new APDeathlinkEvents());
        this.getEventManager().registerListener(new APBouncedEvents());
    }

    @Override
    public void onError(Exception ex) {
        APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.connection.error", ex.toString()).setStyle(Style.EMPTY.withColor(Formatting.RED)));
    }

    @Override
    public void onClose(String reason, int attemptingReconnect) {
        APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.connection.error", reason).setStyle(Style.EMPTY.withColor(Formatting.RED)));
        if (attemptingReconnect > 0) {
            APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.connection.reconnecting", attemptingReconnect));
        }
        slotData = null;
    }

    // Helper Methods //////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Runs a Command on the Archipelago Client if it's present, otherwise runs a separate method
     * @param success the method to run if the Client is present
     * @param fail the method to run if the Client isn't present
     * @return returns 1 if the client is present, otherwise returns 0. Is used for Minecraft Text commands
     */
    public static int runCommand(Consumer<Archipelago> success, Runnable fail) {
        if (!run(success)) {
            fail.run();
            return 0;
        }
        return 1;
    }

    /**
     * Runs an action on the Archipelago Client if it's present
     * @param consumer the method to run
     * @return returns true if the Client is present
     */
    public static boolean run(Consumer<Archipelago> consumer) {
        if (archipelago != null) {
            consumer.accept(archipelago);
            return true;
        }
        return false;
    }

    // Slot Data Methods ///////////////////////////////////////////////////////////////////////////////////////////////

    public static MCSlotData initSlotData(ConnectionResultEvent event) {
        slotData = event.getSlotData(MCSlotData.class);
        return slotData;
    }

    public static @Nullable MCSlotData getSlotData() {
        return slotData;
    }

    public static int getFromSlot(Function<MCSlotData, Integer> function) {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        if (slot != null) {
            return function.apply(slot);
        }
        return -1;
    }

    public static boolean excludesMythicals() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.randomized_mythicals == 0;
    }

    public static boolean excludesLegendaries() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.randomized_legendaries == 0;
    }

    public static boolean excludesTerminusTowers() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.randomized_terminus_towers == 0;
    }

    public static boolean excludesQuestItems() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.randomized_quest_items == 0;
    }

    public static boolean excludesRelics() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.randomized_relics == 0;
    }

    public static boolean regionLocks() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.randomized_terminus_towers == 2;
    }

    public static boolean scoutLocations() {
        Archipelago.MCSlotData slot = Archipelago.getSlotData();
        return slot.scout_tedious_locations == 1;
    }

    //public static boolean hasQOLSetting(String setting) {
    //    Archipelago.MCSlotData slot = Archipelago.getSlotData();
    //    return slot != null && slot.time_saving_options.contains(setting);
    //}

    public static class MCSlotData {
        public String world_version;

        public int goal_condition;

        public int rubies_to_goal;
        public int total_rubies;

        public int deathlink;
        public int traplink;

        public Set<String> excluded_advancements; 

        public Set<String> randomized_abilities;
        public Set<String> possible_randomized_abilities;

        public int randomized_mythicals;
        public int randomized_legendaries;
        public int randomized_terminus_towers;
        public int randomized_quest_items;
        public int randomized_relics;
        public int scout_tedious_locations;
//        public long seed;
//        public int randomize_mob_spawns;
    }
}
