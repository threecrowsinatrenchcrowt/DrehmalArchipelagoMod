package net.threecrows.drehmal_archipelago.archipelago.items.type.traps;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.threecrows.drehmal_archipelago.archipelago.items.type.AbstractAPItem;

public class BeeTrap extends AbstractTrapItem {
    @Override
    public void applyReward(ServerPlayerEntity player) {
        for (int i = 0; i < 6; i++) {
            BeeEntity beeEntity = new BeeEntity(EntityType.BEE, player.getWorld());

            beeEntity.setPos(
                    player.getX() + player.getRandom().nextBetween(-3, 3),
                    player.getEyeY(),
                    player.getZ() + player.getRandom().nextBetween(-3, 3)
            );

            beeEntity.setTarget(player);
            player.getWorld().spawnEntity(beeEntity);
        }
    }
}
