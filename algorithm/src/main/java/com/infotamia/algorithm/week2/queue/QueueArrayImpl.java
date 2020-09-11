package com.infotamia.algorithm.week2.queue;

import java.util.Iterator;

public class QueueArrayImpl<Item> implements Iterable<Item> {
    private Item[] items;
    private int n = 0;
    private int HEAD = 0;
    private int TAIL = 0;

    public QueueArrayImpl() {
        this.items = (Item[]) new Object[1];
    }

    public QueueArrayImpl(int capacity) {
        this.items = (Item[]) new Object[capacity];
    }

    public void enqueue(Item item) {
        if (n == items.length) resize(2 * items.length);
        int oldTail = n;
        items[n++] = item;
        TAIL = oldTail;
    }

    public Item dequeue() {
        int oldHead = HEAD;
        Item item = items[HEAD++];
        items[oldHead] = null;
        n--;
        if (n > 0 && n == items.length / 4) resize(items.length / 2);
        if (isEmpty()) {
            reset();
        }
        return item;
    }

    public int size() {
        return n;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    private void resize(int size) {
        Item[] arrCopy = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            arrCopy[i] = items[i];
        }
        items = arrCopy;
    }

    private void reset() {
        Item[] arr = (Item[]) new Object[1];
        HEAD = 0;
        TAIL = 0;
        n = 0;
        items = arr;
    }

    @Override
    public Iterator<Item> iterator() {
        return new QueueArrayIterable();
    }

    class QueueArrayIterable implements Iterator<Item> {

        int i = n;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public Item next() {
            return items[--i];
        }

        public int size() {
            return n;
        }
    }
}
