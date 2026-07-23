package net.threecrows.drehmal_archipelago.archipelago.items.type.filler;

import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.server.network.ServerPlayerEntity;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;
import net.threecrows.drehmal_archipelago.init.APItems;

public class PotionAPItem extends AbstractAPItem {
    private final Potion potion;

    public PotionAPItem(Potion potion) {
        this.potion = potion;
    }

    @Override
    public void applyReward(ServerPlayerEntity player) {
        ItemStack stack = new ItemStack(APItems.SINGLE_USE_POTION);
        PotionUtil.setPotion(stack, this.potion);
        giveItem(player, stack);
    }
}
