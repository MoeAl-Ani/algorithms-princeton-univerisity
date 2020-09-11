package com.infotamia.algorithm.week6.indirected.mst;

import com.infotamia.algorithm.week1.UF;
import com.infotamia.algorithm.week2.queue.QueueArrayImpl;
import com.infotamia.algorithm.week4.MinPQ;
import com.infotamia.algorithm.week6.indirected.Edge;
import com.infotamia.algorithm.week6.indirected.WeightedGraph;

public class KruskalMST {

    private QueueArrayImpl<Edge> mst = new QueueArrayImpl<>();

    public KruskalMST(WeightedGraph G) {
        MinPQ<Edge> pq = new MinPQ<>(G.V());
        for (Edge e : G.edges()) {
            pq.insert(e);
        }
        UF uf = new UF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() -1) {
            Edge edge = pq.delMin();
            int v = edge.either(); int w = edge.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.enqueue(edge);
            }
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return 0.0;
    }
}
