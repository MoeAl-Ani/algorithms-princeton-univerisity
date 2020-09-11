package com.infotamia.algorithm.week2.stack;

import java.util.Iterator;

public class StackArrayImpl<Item> implements Iterable<Item> {
    private Item[] items;
    private int N = 0;

    public StackArrayImpl() {
        this.items = (Item[]) new Object[1];
    }

    public void push(Item item) {
        if (N == items.length) resize(2 * items.length);
        items[N++] = item;
    }

    public Item pop() {
        Item item = items[--N];
        items[N] = null;
        if (N > 0 && N == items.length / 4) resize(items.length / 2);
        return item;
    }

    public Item peek() {
        int index = N - 1;
        return items[index];
    }

    public boolean isEmpty() {
        return N == 0;
    }

    private void resize(int size) {
        Item[] arrCopy = (Item[]) new Object[size];
        for (int i = 0; i < N; i++) {
            arrCopy[i] = items[i];
        }
        items = arrCopy;
    }

    @Override
    public Iterator<Item> iterator() {
        return new Iterator<>() {

            int i = N;

            @Override
            public boolean hasNext() {
                return i > 0;
            }

            @Override
            public Item next() {
                return items[--i];
            }
        };
    }
}
