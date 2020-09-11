package com.infotamia.algorithm.week1;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Node {
    int id = 0;
    int rank;
    Node parent;
    // used to traverse the set
    Set<Node> children = new HashSet<>();
    Node largestNodeInSet;

    public Node(int id, int rank, Node parent) {
        this.id = id;
        this.rank = rank;
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Set<Node> getChildren() {
        return children;
    }

    public void addChild(Node node) {
        children.add(node);
    }

    public Node getLargestNodeInSet() {
        return largestNodeInSet;
    }

    public void setLargestNodeInSet(Node largestNodeInSet) {
        this.largestNodeInSet = largestNodeInSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return getId() == node.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
