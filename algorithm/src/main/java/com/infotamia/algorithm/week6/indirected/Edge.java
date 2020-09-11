package com.infotamia.algorithm.week6.indirected;

public class Edge implements Comparable<Edge> {

    private final int v,w;
    private final double weight;
    private int directed;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (v == vertex) return w;
        else return v;
    }

    @Override
    public int compareTo(Edge edge) {
        if (this.weight < edge.weight) return -1;
        else if (this.weight > edge.weight) return +1;
        else return 0;
    }
}
