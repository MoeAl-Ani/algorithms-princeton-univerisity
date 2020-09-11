package com.infotamia.algorithm.week6.directed;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class EdgeWeightedDiGraph {
    private final int vertices;
    private int edgesCount;
    final private List<LinkedList<DirectedEdge>> adj;

    public EdgeWeightedDiGraph(int vertices) {
        this.vertices = vertices;
        this.edgesCount = 0;

        adj = new ArrayList<>(this.vertices);
        for (int i = 0; i < this.vertices; i++) {
            adj.add(i, new LinkedList<>());
        }
    }

    public void addEdge(DirectedEdge e) {
        int v = e.from();
        adj.get(v).add(e);
    }

    public Iterable<DirectedEdge> adj(int v) {
     return adj.get(v);
    }

    public int V() {
        return vertices;
    }

    public int E() {
        return edgesCount;
    }

    public Iterable<DirectedEdge> edges() {
        return null;
    }
}
