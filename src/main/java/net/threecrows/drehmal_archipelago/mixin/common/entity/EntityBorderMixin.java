package net.threecrows.drehmal_archipelago.mixin.common.entity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.threecrows.drehmal_archipelago.archipelago.regions.Edge;
import net.threecrows.drehmal_archipelago.common.regions.BorderCollision;
import net.threecrows.drehmal_archipelago.common.regions.BorderMessaging;

@Mixin(Entity.class)
public abstract class EntityBorderMixin {

    @Unique private Vec3d archipelago$prePos;

    @Inject(method = "move", at = @At("HEAD"))
    private void archipelago$captureStart(MovementType type, Vec3d movement, CallbackInfo ci) {
        this.archipelago$prePos = ((Entity) (Object) this).getPos();
    }

    @Inject(method = "move", at = @At("TAIL"))
    private void archipelago$clampToBorder(MovementType type, Vec3d movement, CallbackInfo ci) {       
        Entity self = (Entity) (Object) this;
        PlayerEntity player = null;
        if (self instanceof PlayerEntity selfPlayerEntity) {
            player = selfPlayerEntity;
        }
        for (Entity passenger : self.getPassengerList()) {
            if (passenger instanceof PlayerEntity passengerPlayerEntity) {
                player = passengerPlayerEntity;
            }
        }
        if (player == null) { return; }

        boolean isClientSide = self.getWorld().isClient();
        boolean isLocalPlayer = isClientSide && self == MinecraftClient.getInstance().player;
        boolean isLocalVehicle = isClientSide && self.getPassengerList().contains(MinecraftClient.getInstance().player);
        if (isClientSide && !isLocalPlayer && !isLocalVehicle) return;

        double radius = self.getWidth() / 2.0;
        Vec3d postPos = self.getPos();

        // 1. did we cross a wall entirely this tick?
        Vec3d swept = BorderCollision.resolveSwept(archipelago$prePos, postPos, radius);
        Vec3d working = swept != null ? swept : postPos;

        // 2. resting overlap check (also catches the "standing against it" case)
        Vec3d resting = BorderCollision.resolve(working, radius, player);
        Vec3d finalPos = resting != null ? resting : working;

        if (!finalPos.equals(postPos)) {
            self.setPosition(finalPos.x, finalPos.y, finalPos.z);
            adjustVelocity(self, BorderCollision.getLastPushingEdge());
            BorderMessaging.notifyBlocked(player, BorderCollision.getLastPushingEdge());
        }
    }

    private static void adjustVelocity(Entity self, Edge edge) {
        if (edge == null) return;

        double ex = edge.getEnd().x() - edge.getStart().x();
        double ez = edge.getEnd().z() - edge.getStart().z();
        double len = Math.sqrt(ex * ex + ez * ez);
        if (len < 1e-9) return;

        double tx = ex / len, tz = ez / len;   // tangent (along the wall)
        double nx = -tz, nz = tx;              // normal (perpendicular to the wall)

        Vec3d vel = self.getVelocity();
        double tangentComponent = vel.x * tx + vel.z * tz;

        // keep only the tangential part; drop the component pushing into/out of the wall
        self.setVelocity(tx * tangentComponent, vel.y, tz * tangentComponent);
    }
}