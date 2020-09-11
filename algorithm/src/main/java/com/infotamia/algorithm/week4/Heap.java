package com.infotamia.algorithm.week4;

public class Heap {

    public static void sort(Object[] pq) {
        int N = pq.length;
        for (int k = N/2; k>=1; k--) {
            sink(pq, k, N);
        }
        while (N > 1) {
            swap(pq, 1, N);
            sink(pq, 1, --N);
        }
    }

    public static void sink(Object[] pq, int k, int N) {
        while (2 * k <= N) {
            int j = 2 * k;
            if (j < N && less(pq, j, j+1)) j++;
            if (!less(pq, k, j)) break;
            swap(pq, k, j);
            k = j;
        }
    }

    private static boolean less(Object[] pq, int i, int j) {
        return ((Comparable)pq[i-1]).compareTo((Comparable)pq[j-1]) < 0;
    }

    private static void swap(Object[] a, int i, int j) {
        Object temp = a[i-1];
        a[i-1] = a[j-1];
        a[j-1] = temp;
    }

    public static void main(String[] args) {
        String[] randomString = {"E","E","G","M","R","A","C","E","R","T"};
        int[] xx = {1,2,3,4,-5};
        Heap.sort(randomString);
        Heap.sort(randomString);
        for (String s : randomString) {
            System.out.print(s);
        }
        System.out.println();
        for (Integer s : xx) {
            System.out.print(s);
        }
    }
}
