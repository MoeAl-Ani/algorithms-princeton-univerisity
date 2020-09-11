import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] q;
    private int n;

    public RandomizedQueue() {
        q = (Item[]) new Object[1];
        n = 0;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == q.length) resize(2 * q.length);
        q[n++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        Item item = q[index];
        if (index != n - 1) {
            for (int i = index; i < n - 1; i++) {
                q[i] = q[i + 1];
            }
            q[--n] = null;
        } else {
            q[--n] = null;
        }
        if (n > 0 && n == q.length / 4)
            resize(q.length / 2);
        return item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException();
        int index = StdRandom.uniform(n);
        return q[index];
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private void resize(int size) {
        Item[] arrCopy = (Item[]) new Object[size];
        for (int i = 0; i < n; i++) {
            arrCopy[i] = q[i];
        }
        q = arrCopy;
    }

    private class QueueIterator implements Iterator<Item> {

        private int size = n;
        final private Item[] copy = (Item[]) new Object[n];

        public QueueIterator() {
            for (int i = 0; i < n; i++) {
                copy[i] = q[i];
            }
        }

        @Override
        public boolean hasNext() {
            return size > 0;
        }

        @Override
        public Item next() {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            int index = StdRandom.uniform(0, size);
            Item item = copy[index];
            if (index != n - 1) {
                for (int i = index; i < size - 1; i++) {
                    copy[i] = copy[i + 1];
                }
                copy[--size] = null;
            } else {
                copy[--size] = null;
            }
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("A");
        rq.enqueue("B");
        rq.enqueue("C");
        rq.enqueue("C");
        rq.enqueue("E");
        rq.enqueue("F");
        for (String i : rq)
            System.out.println(i);

        String dequeue = rq.dequeue();
        System.out.println(dequeue);
        System.out.println(rq.size());
        System.out.println(rq.isEmpty());
        System.out.println(rq.sample());
    }

}
