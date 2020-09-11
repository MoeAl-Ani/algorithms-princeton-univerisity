import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private final Set<Point2D> points;

    public PointSET() {
        points = new TreeSet<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        points.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.isEmpty()) return false;
        return points.contains(p);

    }

    public void draw() {
        points.forEach(Point2D::draw);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> res = new ArrayList<>();
        points.forEach(p -> {
            if (rect.contains(p)) res.add(p);
        });
        return res;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (points.isEmpty()) return null;
        int index = 0;
        Point2D nearest = null;
        for (Point2D point : points) {
            if (index == 0) {
                nearest = point;
                index++;
            }
            else {
                if (nearest.distanceSquaredTo(p) > point.distanceSquaredTo(p)) {
                    nearest = point;
                }
            }
        }
        return nearest;

    }

    public static void main(String[] args) {
        PointSET pointSET = new PointSET();
        pointSET.insert(new Point2D(0.5, 1.0));
        pointSET.insert(new Point2D(0.2, 2.0));
        pointSET.insert(new Point2D(0.9, 0.5));
        StdOut.println(pointSET.isEmpty());
        StdOut.println(pointSET.size());
        StdOut.println(pointSET.range(new RectHV(0.0, 0.0, 1.0, 2.0)));
        StdOut.println(pointSET.nearest(new Point2D(0.3, 1.2)));
    }
}
