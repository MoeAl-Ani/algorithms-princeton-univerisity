import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FastCollinearPoints {
    private final LineSegment[] segments;

    public FastCollinearPoints(Point[] points) {
        validatePoints(points);

        List<LineSegment> localSegs = new ArrayList<>();
        Point[] copyOuter = Arrays.copyOf(points, points.length);
        Arrays.sort(copyOuter);

        if (copyOuter.length > 3) {
            Point[] temp = copyOuter.clone();
            for (int i = 0; i < copyOuter.length; i++) {
                Point refPoint = copyOuter[i];
                Arrays.sort(temp, refPoint.slopeOrder());

                List<Point> collinearPoints = new ArrayList<>();
                double slop = refPoint.slopeTo(temp[1]);
                for (int j = 2; j < temp.length; j++) {
                    double calculatedSlop = refPoint.slopeTo(temp[j]);
                    if (slop == calculatedSlop) {
                        collinearPoints.add(temp[j]);
                        collinearPoints.add(temp[j - 1]);
                        if (collinearPoints.size() >= 3) {
                            collinearPoints.add(refPoint);
                            Collections.sort(collinearPoints);
                            Point p1 = collinearPoints.get(0);
                            Point p2 = collinearPoints.get(collinearPoints.size() - 1);
                            if (p1.compareTo(refPoint) == 0) {
                                localSegs.add(new LineSegment(p1, p2));
                            }
                            collinearPoints.clear();
                            break;
                        }
                    }
                    else {
                        slop = calculatedSlop;
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

        //print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
