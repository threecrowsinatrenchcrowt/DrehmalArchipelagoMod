package net.threecrows.drehmal_archipelago.archipelago.locations.advancement;

import static net.threecrows.drehmal_archipelago.archipelago.locations.APLocations.*;

import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.APMod;

public class DrehmalMythicals {
    public static void addDrehmalMythicals() {
        advancement(new Identifier("rando", "locations/mythicals/ascendance"));
        advancement(new Identifier("rando", "locations/mythicals/calamity"));
        advancement(new Identifier("rando", "locations/mythicals/frenzy"));
        advancement(new Identifier("rando", "locations/mythicals/malevolentia"));
        advancement(new Identifier("rando", "locations/mythicals/inert_mythbreaker"));
        advancement(new Identifier("rando", "locations/mythicals/true_mythbreaker"));
        advancement(new Identifier("rando", "locations/mythicals/oblivion"));
        advancement(new Identifier("rando", "locations/mythicals/syzygy"));
        advancement_scout(new Identifier("rando", "locations/mythicals/zenith"), new Identifier("advancements", "discoveries/foundry"));
    }
}
