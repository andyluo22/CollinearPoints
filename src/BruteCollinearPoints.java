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

    // finds all line segments containing 4 points using brute force
    public BruteCollinearPoints(Point[] points) {
        Point origin = new Point(0, 0);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; j++) {
                    for (int l = k + 1; l < points.length; l++) {
                        Double slope1 = points[i].slopeTo(points[j]);
                        Double slope2 = points[i].slopeTo(points[k]);
                        Double slope3 = points[i].slopeTo(points[l]);

                        if (slope1 == slope2 && slope2 == slope3) { //we are using equivalent relations here specifically transitivity Math 220
                            Double slope1ToOrigin = origin.slopeTo(points[i]);
                            Double slope2ToOrigin = origin.slopeTo(points[j]);
                            Double slope3ToOrigin = origin.slopeTo(points[k]);
                            Double slope4ToOrigin = origin.slopeTo(points[l]);

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

    // the number of line segments
    public int numberOfSegments() {
        return this.numOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[this.numOfSegments];

        for (int i = 0; i < this.numOfSegments; i++) {
            lineSegments[i] = segmentsWithTwoEndpoints.get(i);
        }

        return lineSegments;
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

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}