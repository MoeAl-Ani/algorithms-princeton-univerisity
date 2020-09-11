package com.infotamia.algorithm.week6;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class DepthFirstPaths {

    private boolean[] marked;
    private int[] edgeTo;
    private int s;

    public DepthFirstPaths(Graph G, int s) {
        this.s = s;
        marked = new boolean[G.V()];
        Arrays.fill(marked, false);
        edgeTo = new int[G.V()];
        dfs(G, s);
    }

    private void dfs(Graph G, int s) {
        marked[s] = true;
        for (Integer w : G.adj(s)) {
            if(!marked[w]) {
                dfs(G, w);
                edgeTo[w] = s;
            }
        }
    }

    public boolean hasPathTo(int v) {
        return marked[v];
    }

    public Iterable<Integer> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Deque<Integer> path = new ArrayDeque<>();
        for(int x = v; x!= s; x = edgeTo[x]) {
            path.push(x);
        }
        path.push(s);
        return path;
    }
}
