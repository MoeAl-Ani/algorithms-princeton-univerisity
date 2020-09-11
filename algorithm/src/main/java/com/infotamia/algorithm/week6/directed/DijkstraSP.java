package com.infotamia.algorithm.week6.directed;

import com.infotamia.algorithm.week2.stack.StackLinkListImpl;
import com.infotamia.algorithm.week4.IndexMinPQ;

public class DijkstraSP {
    private final double[] distTo;
    private final DirectedEdge[] edgeTo;
    private IndexMinPQ<Double> pq;

    public DijkstraSP(EdgeWeightedDiGraph G, int s) {
        distTo = new double[G.V()];
        edgeTo = new DirectedEdge[G.V()];
        pq = new IndexMinPQ<>(G.V());

        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        // set distance to source = 0.0
        distTo[s] = 0.0;

        pq.insert(s, 0.0);

        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (DirectedEdge e : G.adj(v)) {
                relax(e);
            }
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public Iterable<DirectedEdge> pathTo(int v) {
        StackLinkListImpl<DirectedEdge> path = new StackLinkListImpl<>();
        if (!hasPathTo(v)) return null;
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    private void relax(DirectedEdge e) {
        int v = e.from();
        int w = e.to();
        if (distTo[w] > distTo(v) + e.getWeight()) {
            distTo[w] = distTo(v) + e.getWeight();
            edgeTo[w] = e;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }
}
