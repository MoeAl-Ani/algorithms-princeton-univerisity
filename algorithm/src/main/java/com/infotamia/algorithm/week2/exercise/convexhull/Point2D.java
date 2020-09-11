package com.infotamia.algorithm.week2.exercise.convexhull;

import java.util.Comparator;

public class Point2D {
    final Double x;
    final Double y;

    public Point2D(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    private class PolarOrder implements Comparator<Point2D> {

        @Override
        public int compare(Point2D q1, Point2D q2) {
            double dy1 = q1.y - y;
            double dy2 = q2.y - y;

            if (dy1 == 0 && dy2 == 0) {
                // TODO
                System.out.println("fuck off");
                return 0;
            } else if (dy1 >= 0 && dy2 < 0) return -1;
            else if (dy2 >= 0 && dy1 < 0) return 1;
            else return -ccw(Point2D.this, q1, q2);
        }
    }

    static Comparator<Point2D> Y_ORDER = Comparator.comparing(p -> p.y);
//    static Comparator<Point2D> SOME_CMP = (a,b) -> {
//        if (a.y < b.y) return -1;
//        else if (a.y == b.y && a.x < b.x) return -1;
//        else return 1;
//    };

    public final Comparator<Point2D> BY_POLAR_ORDER = new PolarOrder();
//    Comparator<Point2D> FUCK = (q1, q2) -> {
//        int orientation = ccw(Point2D.this, q1, q2);
//        if (orientation < 0) return -1;
//        if (orientation > 0) return 1;
//        if (distanceSq(q1, Point2D.this) < distanceSq(q2, Point2D.this)) {
//            return -1;
//        } else {
//            return 1;
//        }
//    };

    private double distanceSq(Point2D p1, Point2D p2) {
        return (p1.x - p2.x) * (p1.x - p2.x) +
                (p1.y - p2.y) * (p1.y - p2.y);
    }

    static int ccw(Point2D a, Point2D b, Point2D c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0) return -1; // clockwise
        else if (area2 > 0) return 1; // counter-clockwise
        else return 0; // collinear
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
