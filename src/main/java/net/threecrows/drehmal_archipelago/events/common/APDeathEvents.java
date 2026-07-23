package net.threecrows.drehmal_archipelago.events.common;

import io.github.archipelagomw.bounce.DeathLinkHandler;
import net.deadlydiamond98.koalalib.util.LootTableHelper;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.TagKey;
import net.threecrows.drehmal_archipelago.APMod;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.init.APDamageTypes;
import net.threecrows.drehmal_archipelago.init.APTags;
import net.threecrows.drehmal_archipelago.util.APServerUtil;

public class APDeathEvents {

    public static void register() {
        ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
            handleDeathlink(entity, damageSource);
            handleExtraLootTables(entity, damageSource);
        });
    }

    private static void handleExtraLootTables(LivingEntity entity, DamageSource damageSource) {
        addLootTable(entity, damageSource, APTags.ADDITIONAL_SKULLS, "more_wither_skulls", "Wither Skulls"); // More Wither Skulls
        addLootTable(entity, damageSource, APTags.ADDITIONAL_RABBIT_FEET, "more_rabbits_foot", "Rabbits Foot"); // More Rabbit Feet
    }

    private static void handleDeathlink(LivingEntity entity, DamageSource damageSource) {
        if (entity instanceof PlayerEntity && !damageSource.isOf(APDamageTypes.DEATHLINK)) {
            Archipelago.run(archipelago -> {
                if (archipelago.getTags().contains(DeathLinkHandler.DEATHLINK_TAG)) {
                    archipelago.sendDeathlink(
                            archipelago.getMyName(),
                            damageSource.getDeathMessage(entity).getString()
                    );
                    APServerUtil.runOnServer(server -> server.getPlayerManager().getPlayerList().forEach(player -> {
                        player.damage(APDamageTypes.of(player.getWorld(), APDamageTypes.DEATHLINK), Float.MAX_VALUE);
                    }));
                }
            });
        }
    }

    /**
     * Makes an Entity Drop Additional Loot
     * @param entity the entity
     * @param damageSource the damage source
     * @param tag the tag
     * @param tableName the name of the loot table
     */
    private static void addLootTable(LivingEntity entity, DamageSource damageSource, TagKey<EntityType<?>> tag, String tableName, String enabledStr) {
        //if (entity.getType().isIn(tag)) {
        //    if (Archipelago.hasQOLSetting(enabledStr)) {
        //        LootTableHelper.addLootToMob(entity, damageSource, APMod.id("entities/extra_loot/" + tableName));
        //    }
        //}
    }
}
