package com.infotamia.algorithm.week2.queue;

public class QueueLinkListImpl<Item> {
    private Node first, last;

    private class Node {
        Item item;
        Node next;
    }

    public QueueLinkListImpl() {

    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        if (isEmpty()) first = last;
        else oldLast.next = last;
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        if (isEmpty()) last = null;
        return item;
    }

    public boolean isEmpty() {
        return first == null;
    }
}