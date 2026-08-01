package net.threecrows.drehmal_archipelago.common.regions;

import java.util.List;

import net.threecrows.drehmal_archipelago.archipelago.regions.Edge;

public class BorderState {
    private static volatile List<Edge> activeSegments = List.of();

    public static void setActiveSegments(List<Edge> segments) { activeSegments = segments; }
    public static List<Edge> getActiveSegments() { return activeSegments; }
}