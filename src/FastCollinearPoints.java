import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.Arrays;

public class FastCollinearPoints {

    // finds all line segments containing 4 or more points
    // Use mergeSort algorithm and comparator?
    public FastCollinearPoints(Point[] points) {
        Point[] copy = Arrays.copyOf(points,points.length);
        for(int i = 0; i < points.length; i++) {
            Arrays.sort(copy, points[i].BY_SLOPE); //Time sort utilizes merge sort and insertion sort which is O(nlogn) which is perfect and it is stable
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return 0;
    }

    // the line segments
    public LineSegment[] segments() {
        return null;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            StdDraw.setPenRadius(0.005);
            StdDraw.setPenColor(StdDraw.BLACK);
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            segment.draw();
        }
        StdDraw.show();
    }
}
