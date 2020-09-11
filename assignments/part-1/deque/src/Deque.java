import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node head;
    private Node tail;
    private int n;

    public Deque() {
        head = null;
        tail = null;
        n = 0;
    }

    private class Node {
        Item item;
        Node next = null;
        Node previous = null;
    }


    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldHead = head;
        head = new Node();
        head.item = item;
        head.next = oldHead;
        if (n == 0) {
            tail = head;
        } else {
            oldHead.previous = head;
        }

        n++;
    }

    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldTail = tail;
        tail = new Node();
        tail.item = item;
        tail.previous = oldTail;
        if (n == 0) {
            head = tail;
        } else {
            oldTail.next = tail;
        }
        n++;
    }

    public Item removeFirst() {
        if (n == 0) throw new NoSuchElementException();

        Item item = head.item;
        if (n == 1) {
            head = null;
            tail = null;
        } else {
            head = head.next;
            head.previous = null;
        }
        n--;
        return item;
    }

    public Item removeLast() {
        if (n == 0) throw new NoSuchElementException();
        Item item = tail.item;
        if (n == 1) {
            head = null;
            tail = null;
        } else {
            tail = tail.previous;
            tail.next = null;
        }
        n--;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = head;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (!hasNext())
                throw new java.util.NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.removeLast();
        deque.addFirst(1);
        deque.removeFirst();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(3);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());
        deque.forEach(System.out::println);
        System.out.println(deque.removeLast());
        System.out.println(deque.removeFirst());
        System.out.println(deque.removeFirst());
    }
}
