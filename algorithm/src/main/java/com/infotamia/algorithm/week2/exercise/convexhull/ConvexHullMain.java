package com.infotamia.algorithm.week2.exercise.convexhull;

public class ConvexHullMain {

    public static void main(String[] args) {
        //convex hull
        Point2D[] points = {new Point2D(0d, 3d), new Point2D(1d, 1d), new Point2D(2d, 2d), new Point2D(4d, 4d),
                new Point2D(0d, 0d), new Point2D(1d, 2d), new Point2D(3d, 1d), new Point2D(3d, 3d)};
        ConvexHull convexHull = new ConvexHull(points);
    }
}
