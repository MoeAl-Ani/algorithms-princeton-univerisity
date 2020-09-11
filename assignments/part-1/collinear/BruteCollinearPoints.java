import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {
    private final LineSegment[] segments;

    public BruteCollinearPoints(Point[] points) {
        validatePoints(points);

        List<LineSegment> localSegs = new ArrayList<>();
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        for (int i = 0; i < copy.length - 3; i++) {
            for (int i1 = i + 1; i1 < copy.length - 2; i1++) {
                for (int i2 = i1 + 1; i2 < copy.length - 1; i2++) {
                    for (int i3 = i2 + 1; i3 < copy.length; i3++) {
                        if (copy[i].slopeTo(copy[i1]) == copy[i].slopeTo(copy[i2]) &&
                                copy[i].slopeTo(copy[i2]) == copy[i].slopeTo(copy[i3])) {
                            LineSegment segment = new LineSegment(copy[i], copy[i3]);
                            if (!localSegs.contains(segment)) localSegs.add(segment);
                        }
                    }
                }
            }
        }
        segments = new LineSegment[localSegs.size()];
        localSegs.toArray(segments);

    }

    public int numberOfSegments() {
        return segments.length;
    }

    public LineSegment[] segments() {
        return segments.clone();
    }

    private void validatePoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        validateNullPoints(points);
        validateDuplicates(points);
    }

    private void validateNullPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
    }

    private void validateDuplicates(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        //draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        BruteCollinearPoints bruteCollinearPoints = new BruteCollinearPoints(points);
        for (LineSegment segment : bruteCollinearPoints.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
