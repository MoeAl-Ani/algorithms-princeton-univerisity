import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.List;

public class KdTree {
    private Node root;
    private int size;

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) {
            root = insert(null, p, true);
            size++;
        }
        else if (!contains(p)) {
            root = insert(root, p, root.isVerticalSplit);
            size++;
        }
    }

    private Node insert(Node node, Point2D point, boolean isVertical) {
        if (node == null) {
            return new Node(point, null, null, isVertical);
        }

        if (node.isVerticalSplit) {
            if (point.x() < node.point.x()) {
                node.left = insert(node.left, point, !isVertical);
            }
            else {
                node.right = insert(node.right, point, !isVertical);
            }
        }
        else {
            if (point.y() < node.point.y()) {
                node.left = insert(node.left, point, true);
            }
            else {
                node.right = insert(node.right, point, true);
            }
        }

        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return contains(root, p);
    }

    private boolean contains(Node node, Point2D point) {
        if (node == null) return false;
        if (node.point.equals(point)) {
            return true;
        }
        if (node.isVerticalSplit) {
            if (point.x() < node.point.x()) {
                return contains(node.left, point);
            }
            else {
                return contains(node.right, point);
            }
        }
        else {
            if (point.y() < node.point.y()) return contains(node.left, point);
            else return contains(node.right, point);
        }
    }

    public void draw() {
        if (!isEmpty()) {
            root.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();
        List<Point2D> res = new ArrayList<>();
        if (!isEmpty()) {
            range(root, rect, res);
        }
        return res;
    }

    private void range(Node node, RectHV rect, List<Point2D> result) {
        if (node == null) return;
        Point2D point = node.point;
        if (rect.contains(point)) {
            result.add(point);
        }

        if (node.isVerticalSplit) {
            if (node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax()) {
                // go left
                // go right
                range(node.left, rect, result);
                range(node.right, rect, result);
            }
            else if (node.point.x() > rect.xmax()) {
                // go left
                range(node.left, rect, result);
            }
            else {
                // go right
                range(node.right, rect, result);
            }
        }
        else {
            if (node.point.y() <= rect.ymax() && node.point.y() >= rect.ymin()) {
                // go left
                // go right
                range(node.left, rect, result);
                range(node.right, rect, result);
            }
            else if (node.point.y() > rect.ymax()) {
                // go left
                range(node.left, rect, result);
            }
            else {
                // go right
                range(node.right, rect, result);
            }
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        if (root == null) return null;
        return nearest(root, p).node.point;
    }

    private NearestQueryResult nearest(Node node, Point2D point) {
        if (node == null) return null;
        double distanceToQueryPoint = node.point.distanceSquaredTo(point);
        NearestQueryResult nearestQueryResult = new NearestQueryResult();
        nearestQueryResult.node = node;
        nearestQueryResult.distance = distanceToQueryPoint;
        NearestQueryResult q1 = nearest(node.left, point);
        NearestQueryResult q2 = nearest(node.right, point);
        if (q1 == null && q2 == null) return nearestQueryResult;
        if (q1 != null && q2 != null) {
            if (q1.distance < q2.distance && q1.distance < nearestQueryResult.distance) {
                return q1;
            }
            else if (q2.distance < q1.distance && q2.distance < nearestQueryResult.distance) {
                return q2;
            }
            else return nearestQueryResult;
        }
        if (q1 != null) {
            if (q1.distance < nearestQueryResult.distance) {
                return q1;
            }
        }
        if (q2 != null) {
            if (q2.distance < nearestQueryResult.distance) {
                return q2;
            }
        }
        return nearestQueryResult;
    }


    private static class Node {
        private final Point2D point;
        private final boolean isVerticalSplit;
        private Node left;
        private Node right;

        private Node(Point2D point, Node left, Node right, boolean isVerticalSplit) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.isVerticalSplit = isVerticalSplit;
        }

        private void draw() {
            if (left != null) {
                left.draw();
            }
            point.draw();
            if (right != null) {
                right.draw();
            }
        }
    }

    private class NearestQueryResult {
        private Node node;
        private double distance;
    }

    public static void main(String[] args) {
        KdTree kdTree = new KdTree();
        kdTree.insert(new Point2D(0.5, 1.0));
        kdTree.insert(new Point2D(0.2, 2.0));
        kdTree.insert(new Point2D(0.9, 0.5));
        kdTree.insert(new Point2D(0.9, 0.5));
        StdOut.println(kdTree.isEmpty());
        StdOut.println(kdTree.size());
        StdOut.println(kdTree.range(new RectHV(0.0, 0.0, 1.0, 2.0)));
        StdOut.println(kdTree.nearest(new Point2D(0.3, 1.2)));
    }
}
