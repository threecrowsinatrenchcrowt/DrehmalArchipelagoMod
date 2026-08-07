package net.threecrows.drehmal_archipelago.events.archipelago;

import io.github.archipelagomw.Client;
import io.github.archipelagomw.events.ArchipelagoEventListener;
import io.github.archipelagomw.events.ConnectionResultEvent;
import io.github.archipelagomw.network.ConnectionResult;
import net.deadlydiamond98.koalalib.init.KoalaLibSounds;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import net.threecrows.drehmal_archipelago.APMod;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.archipelago.locations.APLocations;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.networking.s2c.RegionBordersS2CPacket;
import net.threecrows.drehmal_archipelago.networking.s2c.SendArchipelagoInfoS2CPacket;
import net.threecrows.drehmal_archipelago.networking.s2c.SendUncheckedItemsS2CPacket;
import net.threecrows.drehmal_archipelago.util.APAdvancementHelper;
import net.threecrows.drehmal_archipelago.util.APServerUtil;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;

import java.util.ArrayList;
import java.util.List;

public class APConnectEvents {

    @ArchipelagoEventListener
    public void onConnectionEvent(ConnectionResultEvent event) {
        if (event.getResult() == ConnectionResult.Success) {
            APPersistentState state = APPersistentState.get();
            Archipelago.MCSlotData slot = Archipelago.initSlotData(event);

            String version = slot.world_version;
            Style style = Style.EMPTY.withColor(Formatting.YELLOW);
            if (version != null) {
                if (!version.contains(APMod.VALID_WORLD_VERSION)) {
                    APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.version_message.mismatched", version, APMod.VALID_WORLD_VERSION).setStyle(style));
                    APServerUtil.runOnServer(server -> server.getPlayerManager().getPlayerList().forEach(player ->
                            player.playSound(KoalaLibSounds.MAGIC_FAIL, SoundCategory.PLAYERS, 1, 1))
                    );
                    Archipelago.run(Client::close);
                    return;
                }
            } else {
                APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.version_message.invalid").setStyle(style));
                Archipelago.run(Client::close);
                return;
            }

            List<String> missing = new ArrayList<>();

//            slot.enabled_mods.forEach(modID -> {
//                if (!APMod.isModLoaded(modID)) {
//                    missing.add(modID);
//                }
//            });

//            APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.mods_enabled", slot.enabled_mods.toString()).setStyle(style));

            if (!missing.isEmpty()) {
                APServerUtil.runOnServer(server -> server.getPlayerManager().getPlayerList().forEach(player ->
                        player.playSound(KoalaLibSounds.MAGIC_FAIL, SoundCategory.PLAYERS, 1, 1))
                );
                APServerUtil.sendMessage(Text.translatable("drehmal_archipelago.mod_missing", missing.toString()).setStyle(style.withColor(Formatting.RED)));
                APServerUtil.runOnServer(server -> server.getPlayerManager().broadcast(
                        Text.translatable("drehmal_archipelago.mod_missing_alert")
                                .setStyle(style.withColor(Formatting.RED)),
                        true
                ));
            }

            // Unlocks Optional Abilities that aren't randomized
            slot.possible_randomized_abilities.forEach(ability -> {
                if (!slot.randomized_abilities.contains(ability)) {
                    // Crows: Undoubtedly not the cleanest way to do this but it probably doesn't break anything and it is funny
                    // (For context, this is done so that it fully unlocks progressive abilities, since I've made those optional as well)
                    state.triggerCheck(ability.toLowerCase());
                    state.triggerCheck(ability.toLowerCase());
                    state.triggerCheck(ability.toLowerCase());
                    state.triggerCheck(ability.toLowerCase());
                    state.triggerCheck(ability.toLowerCase());
                    state.triggerCheck(ability.toLowerCase());
                }
            });

            APServerUtil.runOnServer(server -> {
                server.execute(() -> {
                    // Interface automatically with modified datapack
                    boolean datapackReminder = false;
                    Scoreboard scoreboard = server.getScoreboard();
                    ScoreboardObjective rando_pools = scoreboard.getNullableObjective("rando_pools");
                    if (rando_pools == null) {
                        datapackReminder = true;
                    }
                    
                    if (datapackReminder) {
                        server.getPlayerManager().broadcast(Text.of("Datapack not found; please leave the world, replace the datapack with the most recent version of hi_drehmal_rando, and rejoin."), false);
                    } else {
                        int mythicalsIncluded = Archipelago.excludesMythicals() ? 0 : 1;
                        int legendariesIncluded = Archipelago.excludesLegendaries() ? 0 : 1;
                        int terminusTowersIncluded = Archipelago.excludesTerminusTowers() ? 0 : 1;
                        int questItemsIncluded = Archipelago.excludesQuestItems()? 0 : 1;
                        int relicsIncluded = Archipelago.excludesRelics()? 0 : 1;
                        int regionLocks = Archipelago.regionLocks() ? 0 : 0;
                        
                        scoreboard.getPlayerScore("#mythicals", rando_pools).setScore(mythicalsIncluded);
                        scoreboard.getPlayerScore("#legendaries", rando_pools).setScore(legendariesIncluded);
                        scoreboard.getPlayerScore("#terminus_towers", rando_pools).setScore(terminusTowersIncluded);
                        scoreboard.getPlayerScore("#quest_items", rando_pools).setScore(questItemsIncluded);
                        scoreboard.getPlayerScore("#relics", rando_pools).setScore(relicsIncluded);
                        scoreboard.getPlayerScore("#region_locks", rando_pools).setScore(regionLocks);
                        scoreboard.getPlayerScore("#connected", rando_pools).setScore(1);
                    }

                    state.setRegionLocks(Archipelago.regionLocks());
                    // Sync Data
                    server.getPlayerManager().getPlayerList().forEach(player -> {
                        server.getPlayerManager().getAdvancementTracker(player).reload(server.getAdvancementLoader());
                        SendArchipelagoInfoS2CPacket.send(player);
                        SendUncheckedItemsS2CPacket.send(player);
                        RegionBordersS2CPacket.send(player);
                    });
                });
            });

            Archipelago.run(archipelago -> {
                // Enable Deathlink
                if (slot.deathlink != 0) {
                    archipelago.setDeathLinkEnabled(true);
                }
                // Enable Traplink
                if (slot.traplink != 0) {
                    archipelago.addTag("TrapLink");
                }
                // Unlock Advancements that are already received
                archipelago.getLocationManager().getCheckedLocations().forEach(aLong -> {
                    if (APLocations.ADVANCEMENT_LOCATIONS.containsValue(aLong)) {
                        state.putAdvancementId(aLong);
                    }
                });
            });
            APServerUtil.runOnServer(server -> {
                server.execute(() -> {
                    APAdvancementHelper.resyncAdvancements();
                });
            });
        }
    }
}
