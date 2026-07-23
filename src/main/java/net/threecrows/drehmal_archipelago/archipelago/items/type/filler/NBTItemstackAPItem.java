package net.threecrows.drehmal_archipelago.archipelago.items.type.filler;

import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.server.network.ServerPlayerEntity;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;

public class NBTItemstackAPItem extends AbstractAPItem {
    private final ItemConvertible item;
    private final int count;
    private final String nbt;

    public NBTItemstackAPItem(ItemConvertible item, int count, String nbt) {
        this.item = item;
        this.count = count;
        this.nbt = nbt;
    }

    @Override
    public void applyReward(ServerPlayerEntity player) {
        ItemStack stack = new ItemStack(this.item, this.count);
        if (this.nbt != null && !this.nbt.isEmpty()) {
            try {
                NbtCompound nbtCompound = StringNbtReader.parse(this.nbt);
                stack.setNbt(nbtCompound);
            } catch (CommandSyntaxException e) {
                e.printStackTrace(); 
            }
        }
        giveItem(player, stack);
    }
}
