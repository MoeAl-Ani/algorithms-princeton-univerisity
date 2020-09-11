package com.infotamia.algorithm.week4;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MinPQ<Key extends Comparable<Key>> implements Iterable<Key>{
    private Key[] pq;
    private int n;
    private Comparator<Key> comparator;

    public MinPQ(int capacity) {
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public MinPQ(int capacity, Comparator<Key> comparator) {
        this.comparator = comparator;
        pq = (Key[]) new Comparable[capacity + 1];
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void insert(Key x) {
        if (n == pq.length - 1) resize(2 * pq.length);
        pq[++n] = x;
        swim(n);
    }

    public Key delMin() {
        Key max = pq[1];
        swap(1, n--);
        sink(1);
        pq[n +1] = null;
        return max;
    }

    private void swim(int k) {
        while (k > 1 && !less(k/2, k)) {
            swap(k, k/2);
            k = k/2;
        }
    }

    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && !less(j, j+1)) j++;
            if (less(k, j)) break;
            swap(k, j);
            k = j;
        }
    }
    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
        return pq[1];
    }

    private boolean less(int i, int j) {
        if (comparator == null) {
            return pq[i].compareTo(pq[j]) < 0;
        }
        else {
            return comparator.compare(pq[i], pq[j]) < 0;
        }
    }

    private void swap(int i, int j) {
        Key temp = pq[i];
        pq[i] = pq[j];
        pq[j] = temp;
    }

    private void resize(int capacity) {
        assert capacity > n;
        Key[] temp = (Key[]) new Object[capacity];
        for (int i = 1; i <= n; i++) {
            temp[i] = pq[i];
        }
        pq = temp;
    }

    public Iterator<Key> iterator() {
        return new HeapIterator();
    }

    private class HeapIterator implements Iterator<Key> {

        // create a new pq
        private MinPQ<Key> copy;

        // add all items to copy of heap
        // takes linear time since already in heap order so no keys move
        public HeapIterator() {
            if (comparator == null) copy = new MinPQ<>(size());
            else                    copy = new MinPQ<>(size(), comparator);
            for (int i = 1; i <= n; i++)
                copy.insert(pq[i]);
        }

        public boolean hasNext()  { return !copy.isEmpty();                     }
        public void remove()      { throw new UnsupportedOperationException();  }

        public Key next() {
            if (!hasNext()) throw new NoSuchElementException();
            return copy.delMin();
        }
    }

    public static void main(String[] args) {
        MinPQ<Integer> pq = new MinPQ<>(10);
        pq.insert(1);
        pq.insert(2);
        pq.insert(-5);
        pq.insert(10);
        System.out.println("min = " + pq.delMin());
        pq.insert(-5);
        pq.insert(-7);
        System.out.println("min = " + pq.delMin());
    }
}

