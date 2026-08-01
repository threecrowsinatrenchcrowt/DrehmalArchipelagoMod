package net.threecrows.drehmal_archipelago.archipelago.regions;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class RegionManager {
    public static final List<Edge> ALL_EDGES = new ArrayList<>();
    public static final RegistryKey<World> OVERWORLD = World.OVERWORLD;
    public static final RegistryKey<World> LO_DAHR = RegistryKey.of(RegistryKeys.WORLD, Identifier.of("minecraft","lodahr"));
    public static final RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD, Identifier.of("minecraft","space"));
    static {
        // capital valley new edges
        ALL_EDGES.add(new Edge(new Vec2d(-512, 71), new Vec2d(1689, -304), "capital_valley", "gulf_of_drehmal", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(1689, -304), new Vec2d(1335, 1362), "capital_valley", "purity_peaks", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(1335, 1362), new Vec2d(494, 1963), "capital_valley", "heartwood", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(494, 1963), new Vec2d(-5, 946), "capital_valley", "av_sal", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5, 946), new Vec2d(-883, 1079), "capital_valley", "av_sal", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-883, 1079), new Vec2d(-512, 71), "capital_valley", "ebony_veldt", OVERWORLD));
        // av'sal new edges
        ALL_EDGES.add(new Edge(new Vec2d(494, 1963), new Vec2d(-681, 2405), "av_sal", "palisades_heath", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-681, 2405), new Vec2d(-883, 1079), "av_sal", "north_tharxax", OVERWORLD));
        // palisades heath new edges
        ALL_EDGES.add(new Edge(new Vec2d(494, 1963), new Vec2d(474, 2982), "palisades_heath", "heartwood", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(474, 2982), new Vec2d(348, 4112), "palisades_heath", "maels_desolation", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(348, 4112), new Vec2d(-1047, 4127), "palisades_heath", "lorahn_kahl", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1047, 4127), new Vec2d(-681, 2405), "palisades_heath", "south_tharxax", OVERWORLD));
        // gulf of drehmal new edges
        ALL_EDGES.add(new Edge(new Vec2d(-235, -1107), new Vec2d(1092, -1183), "gulf_of_drehmal", "akhlo_rohma", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(1092, -1183), new Vec2d(1689, -304), "gulf_of_drehmal", "akhlo_rohma", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-512, 71), new Vec2d(-235, -1107), "gulf_of_drehmal", "merijool", OVERWORLD));
        // merijool new edges
        ALL_EDGES.add(new Edge(new Vec2d(-1740, -2450), new Vec2d(-1231, -2562), "merijool", "dusk_island", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1231, -2562), new Vec2d(172, -2873), "merijool", "dawn_island", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(172, -2873), new Vec2d(-235, -1107), "merijool", "akhlo_rohma", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-512, 71), new Vec2d(-1801, -52), "merijool", "ebony_veldt", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1801, -52), new Vec2d(-2360, 164), "merijool", "ebony_veldt", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2360, 164), new Vec2d(-2596, -322), "merijool", "ebonfire", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2596, -322), new Vec2d(-1740, -2450), "merijool", "casai", OVERWORLD));
        // casai new edges
        ALL_EDGES.add(new Edge(new Vec2d(-5535, -3222), new Vec2d(-1740, -2450), "casai", "dusk_island", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2596, -322), new Vec2d(-5526, -491), "casai", "ebonfire", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5526, -491), new Vec2d(-5535, -3222), "casai", "outside", OVERWORLD));
        // ebonfire new edges
        ALL_EDGES.add(new Edge(new Vec2d(-2360, 164), new Vec2d(-2465, 637), "ebonfire", "ebony_veldt", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2465, 637), new Vec2d(-3120, 987), "ebonfire", "ebony_veldt", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-3120, 987), new Vec2d(-5546, 1083), "ebonfire", "anyr_nogur", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5546, 1083), new Vec2d(-5526, -491), "ebonfire", "outside", OVERWORLD));
        // ebony veldt new edges
        ALL_EDGES.add(new Edge(new Vec2d(-883, 1079), new Vec2d(-1518, 1079), "ebony_veldt", "north_tharxax", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1518, 1079), new Vec2d(-1747, 1455), "ebony_veldt", "north_tharxax", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1747, 1455), new Vec2d(-2417, 1455), "ebony_veldt", "nimahj_swamp", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2417, 1455), new Vec2d(-3120, 987), "ebony_veldt", "anyr_nogur", OVERWORLD));
        // anyr'nogur new edges
        ALL_EDGES.add(new Edge(new Vec2d(-2417, 1455), new Vec2d(-2429, 2229), "anyr_nogur", "nimahj_swamp", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2429, 2229), new Vec2d(-3072, 3749), "anyr_nogur", "nimahj_swamp", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-3072, 3749), new Vec2d(-5550, 3558), "anyr_nogur", "carmine", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5550, 3558), new Vec2d(-5546, 1083), "anyr_nogur", "outside", OVERWORLD));
        // nimahj swamp new edges
        ALL_EDGES.add(new Edge(new Vec2d(-1747, 1455), new Vec2d(-1769, 2405), "nimahj_swamp", "north_tharxax", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1769, 2405), new Vec2d(-2018, 3283), "nimahj_swamp", "south_tharxax", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2018, 3283), new Vec2d(-3072, 3749), "nimahj_swamp", "south_tharxax", OVERWORLD));
        // north tharxax new edges
        ALL_EDGES.add(new Edge(new Vec2d(-681, 2405), new Vec2d(-1769, 2405), "north_tharxax", "south_tharxax", OVERWORLD));
        // lorahn'kahl new edges
        ALL_EDGES.add(new Edge(new Vec2d(348, 4112), new Vec2d(-43, 7646), "lorahn_kahl", "maels_desolation", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-43, 7646), new Vec2d(-1891, 5804), "lorahn_kahl", "carmine", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1891, 5804), new Vec2d(-1822, 4773), "lorahn_kahl", "carmine", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-1822, 4773), new Vec2d(-1047, 4127), "lorahn_kahl", "south_tharxax", OVERWORLD));
        // south tharxax new edges
        ALL_EDGES.add(new Edge(new Vec2d(-1822, 4773), new Vec2d(-3072, 3749), "south_tharxax", "carmine", OVERWORLD));
        // carmine new edges
        ALL_EDGES.add(new Edge(new Vec2d(-43, 7646), new Vec2d(-3030, 7644), "carmine", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-3030, 7644), new Vec2d(-3030, 6072), "carmine", "hellcrags", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-3030, 6072), new Vec2d(-3689, 5212), "carmine", "hellcrags", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-3689, 5212), new Vec2d(-5537, 5103), "carmine", "hellcrags", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5537, 5103), new Vec2d(-5550, 3558), "carmine", "outside", OVERWORLD));
        // hellcrags new edges
        ALL_EDGES.add(new Edge(new Vec2d(-3030, 7644), new Vec2d(-5537, 7644), "hellcrags", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5537, 7644), new Vec2d(-5537, 5103), "hellcrags", "outside", OVERWORLD));
        // akhlo'rohma new edges
        ALL_EDGES.add(new Edge(new Vec2d(172, -2873), new Vec2d(3393, -2362), "akhlo_rohma", "faehrcyle", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(3393, -2362), new Vec2d(3312, -1293), "akhlo_rohma", "veruhkt_plateau", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(3312, -1293), new Vec2d(2870, -1161), "akhlo_rohma", "grand_pike_canyon", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(2870, -1161), new Vec2d(3006, -159), "akhlo_rohma", "grand_pike_canyon", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(3006, -159), new Vec2d(1689, -304), "akhlo_rohma", "purity_peaks", OVERWORLD));
        // purity peaks new edges
        ALL_EDGES.add(new Edge(new Vec2d(3006, -159), new Vec2d(2700, 1361), "purity_peaks", "spearhead_forest", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(2700, 1361), new Vec2d(1335, 1362), "purity_peaks", "heartwood", OVERWORLD));
        // heartwood new edges
        ALL_EDGES.add(new Edge(new Vec2d(2700, 1361), new Vec2d(2918, 1973), "heartwood", "spearhead_forest", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(2918, 1973), new Vec2d(2875, 2992), "heartwood", "black_jungle", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(2875, 2992), new Vec2d(1775, 3400), "heartwood", "maels_desolation", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(1775, 3400), new Vec2d(474, 2982), "heartwood", "maels_desolation", OVERWORLD));
        // mael's desolation new edges
        ALL_EDGES.add(new Edge(new Vec2d(2875, 2992), new Vec2d(2679, 4868), "maels_desolation", "black_jungle", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(2679, 4868), new Vec2d(2578, 7635), "maels_desolation", "sahd", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(2578, 7635), new Vec2d(-43, 7646), "maels_desolation", "outside", OVERWORLD));
        // black jungle new edges
        ALL_EDGES.add(new Edge(new Vec2d(2918, 1973), new Vec2d(7161, 3101), "black_jungle", "spearhead_forest", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(7161, 3101), new Vec2d(7172, 4724), "black_jungle", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(7172, 4724), new Vec2d(2679, 4868), "black_jungle", "sahd", OVERWORLD));
        // spearhead forest new edges
        ALL_EDGES.add(new Edge(new Vec2d(3006, -159), new Vec2d(4886, -100), "spearhead_forest", "grand_pike_canyon", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(4886, -100), new Vec2d(7170, 870), "spearhead_forest", "highfall_tundra", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(7170, 870), new Vec2d(7161, 3101), "spearhead_forest", "outside", OVERWORLD));
        // grand pike canyon new edges
        ALL_EDGES.add(new Edge(new Vec2d(3312, -1293), new Vec2d(3598, -902), "grand_pike_canyon", "veruhkt_plateau", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(3598, -902), new Vec2d(4529, -1246), "grand_pike_canyon", "veruhkt_plateau", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(4529, -1246), new Vec2d(5175, -1045), "grand_pike_canyon", "highfall_tundra", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(5175, -1045), new Vec2d(4886, -100), "grand_pike_canyon", "highfall_tundra", OVERWORLD));
        // veruhkt plateau new edges
        ALL_EDGES.add(new Edge(new Vec2d(3393, -2362), new Vec2d(4376, -2238), "veruhkt_plateau", "faehrcyle", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(4376, -2238), new Vec2d(4529, -1246), "veruhkt_plateau", "highfall_tundra", OVERWORLD));
        // highfall tundra new edges
        ALL_EDGES.add(new Edge(new Vec2d(4376, -2238), new Vec2d(5664, -2595), "highfall_tundra", "frozen_bite", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(5664, -2595), new Vec2d(7161, -2548), "highfall_tundra", "frozen_bite", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(7161, -2548), new Vec2d(7170, 870), "highfall_tundra", "outside", OVERWORLD));
        // frozen bite new edges
        ALL_EDGES.add(new Edge(new Vec2d(5080, -5101), new Vec2d(7161, -5101), "frozen_bite", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(7161, -5101), new Vec2d(7161, -2548), "frozen_bite", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(4376, -2238), new Vec2d(4318, -3116), "frozen_bite", "faehrcyle", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(4318, -3116), new Vec2d(5080, -5101), "frozen_bite", "faehrcyle", OVERWORLD));
        // faehrcyle new edges
        ALL_EDGES.add(new Edge(new Vec2d(172, -2873), new Vec2d(-306, -5085), "faehrcyle", "dawn_island", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-306, -5085), new Vec2d(5080, -5101), "faehrcyle", "outside", OVERWORLD));
        // island of dusk new edges
        ALL_EDGES.add(new Edge(new Vec2d(-5535, -5106), new Vec2d(-2010, -5106), "dusk_island", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-2010, -5106), new Vec2d(-1231, -2562), "dusk_island", "dawn_island", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(-5535, -3222), new Vec2d(-5535, -5106), "dusk_island", "outside", OVERWORLD));
        // island of dawn new edges
        ALL_EDGES.add(new Edge(new Vec2d(-2010, -5106), new Vec2d(-306, -5085), "dawn_island", "outside", OVERWORLD));
        // sahd new edges
        ALL_EDGES.add(new Edge(new Vec2d(7172, 4724), new Vec2d(7172, 7635), "sahd", "outside", OVERWORLD));
        ALL_EDGES.add(new Edge(new Vec2d(7172, 7635), new Vec2d(2578, 7635), "sahd", "outside", OVERWORLD));
        
        // lo'dahr edges
        ALL_EDGES.add(new Edge(new Vec2d(-2500, -2500), new Vec2d(2500, -2500), "lo_dahr", "outside", LO_DAHR));
        ALL_EDGES.add(new Edge(new Vec2d(2500, -2500), new Vec2d(2500, 2500), "lo_dahr", "outside", LO_DAHR));
        ALL_EDGES.add(new Edge(new Vec2d(2500, 2500), new Vec2d(-2500, 2500), "lo_dahr", "outside", LO_DAHR));
        ALL_EDGES.add(new Edge(new Vec2d(-2500, 2500), new Vec2d(-2500, -2500), "lo_dahr", "outside", LO_DAHR));

        // aphelion edges
        ALL_EDGES.add(new Edge(new Vec2d(-2500, -2500), new Vec2d(2500, -2500), "aphelion", "outside", SPACE));
        ALL_EDGES.add(new Edge(new Vec2d(2500, -2500), new Vec2d(2500, 2500), "aphelion", "outside", SPACE));
        ALL_EDGES.add(new Edge(new Vec2d(2500, 2500), new Vec2d(-2500, 2500), "aphelion", "outside", SPACE));
        ALL_EDGES.add(new Edge(new Vec2d(-2500, 2500), new Vec2d(-2500, -2500), "aphelion", "outside", SPACE));
    }
}
