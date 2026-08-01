package net.threecrows.drehmal_archipelago.archipelago.regions;

import java.util.List;

public class Region {
    private final String id;
    private final List<Edge> edges;

    public Region(String id, List<Edge> edges) {
        this.id = id;
        this.edges = edges;
    }

    public String getId() { return id; }
    public List<Edge> getEdges() { return edges; }
}