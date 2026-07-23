package net.threecrows.drehmal_archipelago.mixin.common.advancement;

import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.advancement.PlayerAdvancementTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;

import io.github.archipelagomw.APResult;
import io.github.archipelagomw.network.client.CreateAsHint;
import net.minecraft.util.Identifier;
import net.threecrows.drehmal_archipelago.archipelago.Archipelago;
import net.threecrows.drehmal_archipelago.archipelago.locations.APLocations;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.util.APAdvancementHelper;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

@Mixin(PlayerAdvancementTracker.class)
public abstract class PlayerAdvancementTrackerMixin {

    /*

    This Mixin is used for granting checks when an Advancement is unlocked

     */


    @Shadow public abstract AdvancementProgress getProgress(Advancement advancement);

    @Inject(method = "grantCriterion", at = @At("RETURN"))
    private void archipelago$grantCriterion(Advancement advancement, String criterionName, CallbackInfoReturnable<Boolean> cir) {
        APPersistentState state = APPersistentState.get();
        Long i = APLocations.ADVANCEMENT_LOCATIONS.get(advancement.getId());
        if (i != null && !state.getAdvancementIds().contains(i)) {
            if (getProgress(advancement).isDone()) {
                state.putAdvancementId(i);
            }
        }
        List<Identifier> scoutingGroup = APLocations.SCOUTING_GROUPS.getOrDefault(advancement.getId(), Collections.emptyList());
        ArrayList<Long> scoutIDs = new ArrayList<>();
        for (Identifier scoutLocation : scoutingGroup) {
            Long locID = APLocations.ADVANCEMENT_LOCATIONS.get(scoutLocation);
            if (locID != null && !state.getAdvancementIds().contains(locID) && Archipelago.scoutLocations()) {
                scoutIDs.add(locID);
            }
        }
        if (!scoutIDs.isEmpty()) {
            Archipelago.run(ap -> ap.scoutLocations(scoutIDs, CreateAsHint.BROADCAST_NEW)); 
        }
    }

    /*@WrapMethod(method = "calculateDisplay")
    private void archipelago$calculateDisplay(Advancement root, Set<Advancement> added, Set<Identifier> removed, Operation<Void> original) {
        original.call(root, added, removed);

        Set<Advancement> mustKeep = new HashSet<>();
        for (Advancement advancement : added) {
            if (APAdvancementHelper.isValidAdvancement(advancement.getId())) {
                Advancement current = advancement;
                while (current != null && mustKeep.add(current)) {
                    current = current.getParent();
                }
            }
        }

        added.removeIf(advancement -> !mustKeep.contains(advancement));
    }*/
}
