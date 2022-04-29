import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import javax.sound.sampled.Line;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class BruteCollinearPoints {
    private int numOfSegments;
    private ArrayList<LineSegment> segmentsWithTwoEndpoints;

    /**
     * Examines only four euclidean points simultaneously and checks whether they lie on the same line segment.
     * Does not include repeated subsegments.
     * @param points euclidean points to be examined
     */
    public BruteCollinearPoints(Point[] points) {
        Point origin = new Point(0, 0);
        this.segmentsWithTwoEndpoints = new ArrayList<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Double slope1 = points[i].slopeTo(points[j]);
                        Double slope2 = points[i].slopeTo(points[k]);
                        Double slope3 = points[i].slopeTo(points[l]);

                        if (slope1.equals(slope2) && slope2.equals(slope3)) { //we are using equivalent relations here specifically transitivity Math 220
                            Double slope1ToOrigin = origin.slopeTo(points[i]);
                            Double slope2ToOrigin = origin.slopeTo(points[j]);
                            Double slope3ToOrigin = origin.slopeTo(points[k]);
                            Double slope4ToOrigin = origin.slopeTo(points[l]);

                            //MAYBE USE A HASHMAP FOR THIS to reduce memory storage if I have time but it really shouldn't matter since big N notation
                            ArrayList<Point> storePoints = new ArrayList<Point>();
                            storePoints.add(points[i]);
                            storePoints.add(points[j]);
                            storePoints.add(points[k]);
                            storePoints.add(points[l]);

                            ArrayList<Double> storeSlopes = new ArrayList<Double>();
                            storeSlopes.add(slope1ToOrigin);
                            storeSlopes.add(slope2ToOrigin);
                            storeSlopes.add(slope3ToOrigin);
                            storeSlopes.add(slope4ToOrigin);

                            Double max = Collections.max(storeSlopes);
                            Double min = Collections.min(storeSlopes);
                            int maxIndex = storeSlopes.indexOf(max);
                            int minIndex = storeSlopes.indexOf(min);

                            LineSegment line = new LineSegment(storePoints.get(maxIndex), storePoints.get(minIndex));
                            this.segmentsWithTwoEndpoints.add(line);
                            numOfSegments++;
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines the number of distinct line segments composed of 4 or
     * more euclidean points that span a line
     * @return the number of distinct line segments
     */
    public int numberOfSegments() {
        return this.numOfSegments;
    }

    /**
     * Holds the distinct line segments that are composed of 4 or
     * more euclidean points
     * @return the distinct line segments
     */
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[this.numOfSegments];

        for (int i = 0; i < this.numOfSegments; i++) {
            lineSegments[i] = segmentsWithTwoEndpoints.get(i);
        }

        return lineSegments;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In("input10000.txt");
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            segment.draw();
        }
        StdDraw.show();
    }
}