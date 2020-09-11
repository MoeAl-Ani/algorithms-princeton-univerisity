package com.infotamia.algorithm.week6;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Graph {
    private final int V;
    private int E;
    private final List<LinkedList<Integer>> adj;

    public Graph(int v) {
        if (v < 0) throw new IllegalArgumentException("number of vertices in a Graph must be nonnegative");
        V = v;
        this.adj = new ArrayList<>(v);
        for (int i = 0; i < this.V; i++) {
            adj.set(i, new LinkedList<>());
        }
    }

    public void addEdge(int v, int w) {
        adj.get(v).add(w);
        adj.get(w).add(v);
        E++;
    }

    public Iterable<Integer> adj(int v) {
        return adj.get(v);
    }

    public int degree(int v) {
        return adj.get(v).size();
    }

    /**
     * number of vertices
     *
     * @return
     */
    public int V() {
        return V;
    }

    /**
     * number of edges
     *
     * @return
     */
    public int E() {
        return E;
    }
}
