package net.threecrows.drehmal_archipelago.common.regions;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import net.threecrows.drehmal_archipelago.archipelago.regions.Edge;
import net.threecrows.drehmal_archipelago.common.world.APPersistentState;
import net.threecrows.drehmal_archipelago.init.APDamageTypes;

public class BorderCollision {

    private static Edge lastPushingEdge; 

    public static Vec3d resolve(Vec3d attemptedPos, double radius, PlayerEntity player) {
        double x = attemptedPos.x;
        double z = attemptedPos.z;
        boolean changed = false;

        Edge closestEdge = null;
        double closestDist = 10000 * 10000;


        for (Edge edge : BorderState.getActiveSegments()) {
            double ax = edge.getStart().x(), az = edge.getStart().z();
            double bx = edge.getEnd().x(), bz = edge.getEnd().z();

            double[] closest = closestPointOnSegment(x, z, ax, az, bx, bz);
            double dx = x - closest[0];
            double dz = z - closest[1];
            double distSq = dx * dx + dz * dz;

            if (distSq < radius * radius) {
                double dist = Math.sqrt(distSq);
                if (dist < 1.0e-6) {
                    dx = -(bz - az);
                    dz = (bx - ax);
                    dist = Math.sqrt(dx * dx + dz * dz);
                }
                double push = radius - dist;
                x += dx / dist * push;
                z += dz / dist * push;
                changed = true;
                lastPushingEdge = edge;
            }

            if (distSq < closestDist)
            {
                closestDist = distSq;
                closestEdge = edge;
            }
        }

        if (closestEdge != null)
        {
           if (!APPersistentState.get().getUnlockedRegionIds().contains(pickUnblockedSide(attemptedPos, closestEdge)))
           {
                //player.sendMessage(Text.literal(pickUnblockedSide(attemptedPos, closestEdge)), false);
                player.damage(APDamageTypes.of(player.getWorld(), APDamageTypes.BORDER_FAILSAFE), Float.MAX_VALUE);
           }
        }

        if (changed) {
            for (int pass = 0; pass < 2; pass++) {
                for (Edge edge : BorderState.getActiveSegments()) {
                    double ax = edge.getStart().x(), az = edge.getStart().z();
                    double bx = edge.getEnd().x(), bz = edge.getEnd().z();

                    double[] closest = closestPointOnSegment(x, z, ax, az, bx, bz);
                    double dx = x - closest[0];
                    double dz = z - closest[1];
                    double distSq = dx * dx + dz * dz;

                    if (distSq < radius * radius) {
                        double dist = Math.sqrt(distSq);
                        if (dist < 1.0e-6) {
                            dx = -(bz - az);
                            dz = (bx - ax);
                            dist = Math.sqrt(dx * dx + dz * dz);
                        }
                        double push = radius - dist;
                        x += dx / dist * push;
                        z += dz / dist * push;
                        changed = true;
                        lastPushingEdge = edge;
                    }
                }
            }
        }

        return changed ? new Vec3d(x, attemptedPos.y, z) : null;
    }

    public static Vec3d resolveSwept(Vec3d from, Vec3d to, double radius) {
        if (from.x == to.x && from.z == to.z) return null; 

        Double earliestT = null;
        Edge hitEdge = null;

        for (Edge edge : BorderState.getActiveSegments()) {
            Double t = segmentIntersectionT(
                from.x, from.z, to.x, to.z,
                edge.getStart().x(), edge.getStart().z(),
                edge.getEnd().x(), edge.getEnd().z());
            if (t != null && (earliestT == null || t < earliestT)) {
                earliestT = t;
                hitEdge = edge;
            }
        }

        if (earliestT == null) return null;

        double pathLength = Math.sqrt((to.x - from.x) * (to.x - from.x) + (to.z - from.z) * (to.z - from.z));
        double backOffT = pathLength <= 0 ? 0 : radius / pathLength;
        double clampT = Math.max(0, earliestT - backOffT);

        lastPushingEdge = hitEdge;
        return from.add(to.subtract(from).multiply(clampT));
    }

    public static Edge getLastPushingEdge() { return lastPushingEdge; }

    private static double[] closestPointOnSegment(double px, double pz, double ax, double az, double bx, double bz) {
        double dx = bx - ax, dz = bz - az;
        double lenSq = dx * dx + dz * dz;
        double t = lenSq == 0 ? 0 : ((px - ax) * dx + (pz - az) * dz) / lenSq;
        t = Math.max(0, Math.min(1, t));
        return new double[] { ax + t * dx, az + t * dz };
    }

    private static Double segmentIntersectionT(double p0x, double p0z, double p1x, double p1z,
                                                double q0x, double q0z, double q1x, double q1z) {
        double rX = p1x - p0x, rZ = p1z - p0z;
        double sX = q1x - q0x, sZ = q1z - q0z;
        double denom = rX * sZ - rZ * sX;
        if (Math.abs(denom) < 1e-9) return null; 

        double qpX = q0x - p0x, qpZ = q0z - p0z;
        double t = (qpX * sZ - qpZ * sX) / denom;
        double u = (qpX * rZ - qpZ * rX) / denom;

        return (t >= 0 && t <= 1 && u >= 0 && u <= 1) ? t : null;
    }

    private static String pickUnblockedSide(Vec3d posVec3d, Edge edge) {
        double ax = edge.getStart().x(), az = edge.getStart().z();
        double bx = edge.getEnd().x(), bz = edge.getEnd().z();
        double nx = -(bz - az), nz = (bx - ax);

        double side = (posVec3d.x - ax) * nx + (posVec3d.z - az) * nz;
        return side >= 0 ? edge.getRegionA() : edge.getRegionB();
    }
}
