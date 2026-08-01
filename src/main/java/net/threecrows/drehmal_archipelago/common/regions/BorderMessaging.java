package net.threecrows.drehmal_archipelago.common.regions;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.threecrows.drehmal_archipelago.archipelago.regions.Edge;

public class BorderMessaging {
    private static final Map<UUID, Long> lastShown = new HashMap<>();
    private static final long COOLDOWN_TICKS = 20;

    public static void notifyBlocked(PlayerEntity player, Edge edge) {
        if (edge == null) return;

        long now = player.getWorld().getTime();
        Long last = lastShown.get(player.getUuid());
        if (last != null && now - last < COOLDOWN_TICKS) return;
        lastShown.put(player.getUuid(), now);

        String blockedRegionId = pickBlockedSide(player, edge);
        if (blockedRegionId == null) return;

        if (player.getWorld().isClient() && player == MinecraftClient.getInstance().player) {
            Text title = blockedRegion(blockedRegionId); 
            MinecraftClient.getInstance().inGameHud.setTitle(title);
            MinecraftClient.getInstance().inGameHud.setTitleTicks(5, 40, 10);
        }
    }

    private static String pickBlockedSide(PlayerEntity player, Edge edge) {
        double ax = edge.getStart().x(), az = edge.getStart().z();
        double bx = edge.getEnd().x(), bz = edge.getEnd().z();
        double nx = -(bz - az), nz = (bx - ax);

        double side = (player.getX() - ax) * nx + (player.getZ() - az) * nz;
        return side >= 0 ? edge.getRegionB() : edge.getRegionA();
    }

    private static Text blockedRegion(String id) {
        switch (id) {
            case "av_sal":
                return Text.of("Av'Sal Locked");
            case "palisades_heath":
                return Text.of("Palisades Heath Locked");
            case "gulf_of_drehmal":
                return Text.of("Gulf of Drehmal Locked");
            case "merijool":
                return Text.of("Merijool Locked");
            case "casai":
                return Text.of("Casai Locked");
            case "ebonfire":
                return Text.of("Mt. Ebonfire Locked");
            case "ebony_veldt":
                return Text.of("Ebony Veldt Locked");
            case "anyr_nogur":
                return Text.of("Anyr'Nogur Locked");
            case "nimahj_swamp":
                return Text.of("Nimahj Swamp Locked");
            case "north_tharxax":
                return Text.of("North Tharxax Locked");
            case "lorahn_kahl":
                return Text.of("Lorahn'Kahl Locked");
            case "south_tharxax":
                return Text.of("South Tharxax Locked");
            case "carmine":
                return Text.of("Carmine Locked");
            case "hellcrags":
                return Text.of("Hellcrags Locked");
            case "akhlo_rohma":
                return Text.of("Akhlo'Rohma Locked");
            case "purity_peaks":
                return Text.of("Purity Peaks Locked");
            case "heartwood":
                return Text.of("North Heartwood Locked");
            case "maels_desolation":
                return Text.of("South Heartwood Locked");
            case "black_jungle":
                return Text.of("Black Jungle Locked");
            case "spearhead_forest":
                return Text.of("Spearhead Forest Locked");
            case "grand_pike_canyon":
                return Text.of("Grand Pike Canyon Locked");
            case "veruhkt_plateau":
                return Text.of("Veruhkt Plateau Locked");
            case "highfall_tundra":
                return Text.of("Highfall Tundra Locked");
            case "frozen_bite":
                return Text.of("Frozen Bite Locked");
            case "faehrcyle":
                return Text.of("Faehrcyle Locked");
            case "dusk_island":
                return Text.of("Island of Dusk Locked");
            case "dawn_island":
                return Text.of("Island of Dawn Locked");
            case "sahd":
                return Text.of("Sahd Locked");
            default:
                return Text.of("Error");
        }
    }
}
