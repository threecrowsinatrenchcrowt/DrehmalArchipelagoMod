package net.threecrows.drehmal_archipelago.archipelago.items.type.traps;

import net.minecraft.entity.TntEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.threecrows.drehmal_archipelago.networking.s2c.LiteratureTrapS2CPacket;

public class LiteratureTrap extends AbstractTrapItem {
    @Override
    public void applyReward(ServerPlayerEntity player) {
        LiteratureTrapS2CPacket.send(player);
    }
}
