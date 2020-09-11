package com.infotamia.algorithm.week2.exercise.convexhull;

import com.infotamia.algorithm.week2.stack.StackArrayImpl;

import java.util.Arrays;

public class ConvexHull {
    public ConvexHull(Point2D[] points) {
        //Stack<Point2D> hull = new Stack<>();
        StackArrayImpl<Point2D> hull = new StackArrayImpl<>();
        Arrays.sort(points, Point2D.Y_ORDER);
        Arrays.sort(points, points[0].BY_POLAR_ORDER);

        hull.push(points[0]);
        hull.push(points[1]);

        for (int i = 2; i < points.length; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }


        hull.forEach(System.out::println);

    }
}
