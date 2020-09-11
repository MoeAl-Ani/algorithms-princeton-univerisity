package com.infotamia.algorithm.week1;

import java.util.ArrayList;
import java.util.List;

public class NodeUF {

    List<Node> rootNodes = new ArrayList<>();

    public NodeUF(int size) {
        // big O(N) during constructions
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Node node = new Node(i, 0, null);
                rootNodes.add(node);
            }
        } else {
            throw new RuntimeException("fuck off");
        }
    }

    public boolean connected(Node p, Node q) {
        return root(p).getId() == root(q).getId();
    }

    private Node root(Node node) {
        Node current = node;
        while (current.getParent() != null) {
            // path compression
            Node nodeParent = current.getParent();
            if (nodeParent.getParent() != null) {
                node.setParent(nodeParent.getParent());
                nodeParent.getChildren().remove(node);
            }
            current = current.getParent();
        }
        return current;
    }

    public void union(Node p, Node q) {
        Node pRoot = root(p);
        Node qRoot = root(q);
        int pNodeId = pRoot.getId();
        int qNodeId = qRoot.getId();
        Node maxP;
        Node maxQ;
        if (pRoot.getLargestNodeInSet() != null) {
            maxP = pRoot.getLargestNodeInSet();
        } else {
            maxP = pRoot;
        }
        if (qRoot.getLargestNodeInSet() != null) {
            maxQ = qRoot.getLargestNodeInSet();
        } else {
            maxQ = qRoot;
        }
        Node max = maxP.getId() >= maxQ.getId() ? maxP : maxQ;
        // same root.
        if (pNodeId == qNodeId) return;
        if (pRoot.getRank() < qRoot.getRank()) {
            pRoot.setParent(qRoot);
            pRoot.setLargestNodeInSet(null);
            qRoot.setLargestNodeInSet(max);
            qRoot.setRank(qRoot.getRank() + 1);
            qRoot.addChild(pRoot);
        } else if (pRoot.getRank() > qRoot.getRank()) {
            qRoot.setParent(pRoot);
            qRoot.setLargestNodeInSet(null);
            pRoot.setLargestNodeInSet(max);
            pRoot.setRank(pRoot.getRank() + 1);
            pRoot.addChild(qRoot);
        } else {
            pRoot.setParent(qRoot);
            pRoot.setLargestNodeInSet(null);
            qRoot.setLargestNodeInSet(max);
            qRoot.setRank(qRoot.getRank() + 1);
            qRoot.addChild(pRoot);
        }
    }


    // return the node of the biggest id in connected set
    public Node find(Node node) {
        return root(node).getLargestNodeInSet();
    }
}
