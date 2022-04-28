import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private int numOfSegments;
    private LineSegment[] segmentsTwoEndpoints;

    // finds all line segments containing 4 points using brute force
    public BruteCollinearPoints(Point[] points) {
        for(int i = 0; i < points.length; i++) {
            for(int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; j++) {
                    for(int l = k + 1; l < points.length; l++) {
                        Double slope1 = points[i].slopeTo(points[j]);
                        Double slope2 = points[i].slopeTo(points[k]);
                        Double slope3 = points[i].slopeTo(points[l]);

                        if(slope1 == slope2 && slope2 == slope3) { //we are using equivalent relations here specifically transitivity Math 220
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
        return null;
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