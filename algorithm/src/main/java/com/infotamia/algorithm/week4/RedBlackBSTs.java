package com.infotamia.algorithm.week4;

import java.util.ArrayDeque;
import java.util.Queue;

public class RedBlackBSTs<Key extends Comparable<Key>, Value> {

    private Node root;

    public void put(Key key, Value value) {
        root = put(root, key, value);
    }

    private Node put(Node x, Key key, Value value) {
        if (x == null) return new Node(key, value, Color.RED);
        int cmp = key.compareTo(x.key);
        if (cmp < 0)
            x.left = put(x.left, key, value);
        else if (cmp > 0)
            x.right = put(x.right, key, value);
        else x.value = value;

        if (isRed(x.right) && !isRed(x.left)) x = rotateLeft(x);
        if (isRed(x.left) && isRed(x.left.left)) x = rotateRight(x);
        if (isRed(x.left) && isRed(x.right)) flipColor(x);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Value get(Key key) {
        Node x = root;
        while (x != null) {
            int cmp = key.compareTo(x.key);
            if (cmp < 0) x = x.left;
            else if (cmp > 0) x = x.right;
            else return x.value;
        }
        return null;
    }

    public void delete(Key key) {
        root = delete(root, key);
    }

    private Node delete(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) x.left = delete(x.left, key);
        else if (cmp > 0) x.right = delete(x.right, key);
        else {
            if (x.left == null && x.right == null) return null;
            if (x.left == null) return x.right;
            if (x.right == null) return x.left;
            // find the minimum node from the right
            Node t = x;
            x = findMin(x.right);
            x.right = deleteMin(t.right);
            x.left = t.left;
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public void deleteMin() {
        root = deleteMin(root);
    }

    private Node deleteMin(Node x) {
        if (x.left == null) return x.right;
        x.left = deleteMin(x.left);
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    public Key findMin() {
        Node min = findMin(root);
        if (min == null) return null;
        return min.key;
    }

    public Key findMax() {
        Node max = findMax(root);
        if (max == null) return null;
        return max.key;
    }

    private Node findMax(Node x) {
        if (x == null) return null;
        if (x.right == null) return x;
        return findMin(x.right);
    }

    private Node findMin(Node x) {
        if (x == null) return null;
        if (x.left == null) return x;
        return findMin(x.left);
    }

    public int size() {
        return size(root);
    }

    private int size(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    public Key floor(Key key) {
        Node x = floor(root, key);
        if (x == null) return null;
        return x.key;
    }

    public int size(Key lo, Key hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to size() is null");

        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }

    public boolean contains(Key key) {
        return get(key) != null;
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private Node floor(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if (cmp == 0) return x;
        if (cmp < 0)
            return floor(x.left, key);
        Node t = floor(x.right, key);
        if (t != null) return t;
        else return x;
    }

    private int rank(Node x, Key key) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if (cmp < 0) return rank(x.left, key);
        else if (cmp > 0) return 1 + size(x.left) + rank(x.right, key);
        else return size(x.left);
    }

    public Iterable<Key> keys() {
        Queue<Key> q = new ArrayDeque<>();
        inorder(root, q);
        return q;
    }

    private void inorder(Node x, Queue<Key> q) {
        if (x == null) return;
        inorder(x.left, q);
        q.add(x.key);
        inorder(x.right, q);
    }

    public int height() {
        return height(root);
    }

    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }

    private Node rotateLeft(Node h) {
        Node x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = Color.RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    private Node rotateRight(Node h) {
        Node x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = Color.RED;
        x.size = h.size;
        h.size = 1 + size(h.left) + size(h.right);
        return x;
    }

    private void flipColor(Node h) {
        h.color = Color.RED;
        h.left.color = Color.BLACK;
        h.right.color = Color.BLACK;
    }

    private class Node {
        private Key key;
        private Value value;
        private Node left, right;
        private int size;
        private Color color;

        public Node(Key key, Value value, Color color) {
            this.key = key;
            this.value = value;
            this.size = 1;
            this.color = color;
        }

        @Override
        public String toString() {
            return key.toString();
        }
    }

    private boolean isRed(Node x) {
        if (x == null) return false;
        return x.color == Color.RED;
    }

    private enum Color {
        RED, BLACK
    }

    public static void main(String[] args) {
        RedBlackBSTs<String, Integer> bst = new RedBlackBSTs<>();
        bst.put("S", 1);
        bst.put("E", 2);
        bst.put("A", 3);
        bst.put("R", 4);
        bst.put("C", 5);
        bst.put("H", 6);
        bst.put("L", 6);
        bst.put("P", 6);
        bst.put("X", 6);
        bst.put("M", 6);

        int range = bst.size("F", "T");
        bst.keys().forEach(System.out::println);
    }
}

