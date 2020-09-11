package com.infotamia.algorithm.week6;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Queue;

public class BreadthFirstPaths {

    private boolean[] marked;
    private int[] edgeTo;
    private int s;
    private int count = 0;
    private int[] distTo;

    public BreadthFirstPaths(Graph G, int s) {
        this.s = s;
        marked = new boolean[G.V()];
        Arrays.fill(marked, false);
        edgeTo = new int[G.V()];
        distTo = new int[G.V()];
        bfs(G, s);
    }

    private void bfs(Graph G, int s) {
        count++;
        Queue<Integer> q = new ArrayDeque<>();
        Arrays.fill(distTo, Integer.MAX_VALUE);
        q.offer(s);
        marked[s] = true;
        distTo[s] = 0;

        while (!q.isEmpty()) {
            Integer v = q.poll();
            for (int w : G.adj(v)) {
                if (!hasPathTo(v)) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = 1 + distTo[v];
                    q.offer(w);
                }
            }
        }
    }

    public int count() {
        return count;
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
