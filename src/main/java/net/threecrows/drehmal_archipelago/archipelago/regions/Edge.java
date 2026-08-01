package net.threecrows.drehmal_archipelago.archipelago.regions;

import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public class Edge {
    private final Vec2d start;
    private final Vec2d end;
    private final String regionA;
    private final String regionB;
    private final RegistryKey<World> dimension;

    public Edge(Vec2d start, Vec2d end, String regionA, String regionB, RegistryKey<World> dimension) {
        this.start = start;
        this.end = end;
        this.regionA = regionA;
        this.regionB = regionB;
        this.dimension = dimension;
    }

    public Vec2d getStart() { return start; }
    public Vec2d getEnd() { return end; }
    public String getRegionA() { return regionA; }
    public String getRegionB() { return regionB; }
    public RegistryKey<World> getDimension() { return dimension; }

    public String getOtherRegion(String regionId) {
        if (regionId.equals(regionA)) return regionB;
        if (regionId.equals(regionB)) return regionA;
        throw new IllegalArgumentException("Region " + regionId + " is not adjacent to this edge");
    }

    public boolean isMapBoundary() {
        return regionA == null || regionB == null;
    }
}