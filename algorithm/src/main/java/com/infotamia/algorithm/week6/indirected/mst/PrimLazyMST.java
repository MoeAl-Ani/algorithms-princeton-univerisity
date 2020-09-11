package com.infotamia.algorithm.week6.indirected.mst;

import com.infotamia.algorithm.week2.queue.QueueArrayImpl;
import com.infotamia.algorithm.week4.MinPQ;
import com.infotamia.algorithm.week6.indirected.Edge;
import com.infotamia.algorithm.week6.indirected.WeightedGraph;

public class PrimLazyMST {

    private boolean[] marked;
    private MinPQ<Edge> pq;
    private QueueArrayImpl<Edge> mst;

    public PrimLazyMST(WeightedGraph G) {
        pq = new MinPQ<>(G.V());
        mst = new QueueArrayImpl<>(G.V());
        marked = new boolean[G.V()];

        visit(G, 0);

        while (!pq.isEmpty()) {
            Edge e = pq.delMin();
            int v = e.either(); int w = e.other(v);
            if (marked[v] && marked[w]) continue;
            mst.enqueue(e);
            if (!marked[v]) visit(G, v);
            if (!marked[w]) visit(G, w);
        }
    }

    private void visit(WeightedGraph G, int v) {
        marked[v] = true;
        for (Edge e : G.edges()) {
            if (!marked[e.other(v)]) {
                pq.insert(e);
            }
        }
    }
}
