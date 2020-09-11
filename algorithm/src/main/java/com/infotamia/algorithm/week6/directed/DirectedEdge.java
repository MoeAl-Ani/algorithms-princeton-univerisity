package com.infotamia.algorithm.week6.directed;

public class DirectedEdge implements Comparable<DirectedEdge> {

    private int v, w;
    private final double weight;

    public DirectedEdge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public int from() {
        return v;
    }

    public int to() {
        return w;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public int compareTo(DirectedEdge directedEdge) {
        return 0;
    }
}
