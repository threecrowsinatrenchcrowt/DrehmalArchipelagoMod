package net.threecrows.drehmal_archipelago.archipelago.locations;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.APMod;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.archipelago.locations.advancement.DrehmalLegendaries;
import net.threecrows.drehmal_archipelago.archipelago.locations.advancement.DrehmalMythicals;
import net.threecrows.drehmal_archipelago.archipelago.locations.advancement.DrehmalQuestItems;
import net.threecrows.drehmal_archipelago.archipelago.locations.advancement.DrehmalRelics;
import net.threecrows.drehmal_archipelago.archipelago.locations.advancement.DrehmalTerminusTowers;
import net.threecrows.drehmal_archipelago.archipelago.locations.advancement.VanillaAdvancements;
import net.threecrows.drehmal_archipelago.util.tracker.IAbilityCheck;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.List;
import java.util.ArrayList;

public class APLocations {
    public static final BiMap<Identifier, Long> ADVANCEMENT_LOCATIONS = HashBiMap.create();
    public static final BiMap<Identifier, Long> ITEMSANITY_LOCATIONS = HashBiMap.create();
    public static final Map<Identifier, List<Identifier>> SCOUTING_GROUPS = new HashMap<>();
    private static long id = 1;

    static {
        DrehmalMythicals.addDrehmalMythicals();
        DrehmalLegendaries.addDrehmalLegendaries();
        DrehmalTerminusTowers.addDrehmalTerminusTowers();
        DrehmalQuestItems.addDrehmalQuestItems();
        DrehmalRelics.addDrehmalRelics();
        // Vanilla Locations
        VanillaAdvancements.addVanillaAdvancements();

    }

    // CREATE //////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static void advancement(Identifier advancementID) {
        ADVANCEMENT_LOCATIONS.put(advancementID, id++);
    }

    public static void advancement_scout(Identifier advancementID, Identifier scoutingParentID) {
        ADVANCEMENT_LOCATIONS.put(advancementID, id++);
        SCOUTING_GROUPS.computeIfAbsent(scoutingParentID, k -> new ArrayList<>()).add(advancementID);
    }

    //public static void itemsanity(Identifier itemID) {
    //    ITEMSANITY_LOCATIONS.put(itemID, id++);
    //}
}
