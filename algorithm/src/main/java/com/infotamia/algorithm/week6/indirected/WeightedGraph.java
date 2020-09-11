package com.infotamia.algorithm.week6.indirected;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class WeightedGraph {
    private int V;
    private int E = 0;
    private final List<LinkedList<Edge>> adj;

    public WeightedGraph(int v) {
        V = v;
        adj = new ArrayList<>(this.V);
        for (int i = 0; i < this.V; i++) {
            adj.set(i, new LinkedList<>());
        }
    }

    public void addEdge(Edge e) {
        int v = e.either();
        int w = e.other(v);
        adj.get(v).add(e);
        adj.get(w).add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj.get(v);
    }

    public Iterable<Edge> edges() {
        return null;
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }


}
