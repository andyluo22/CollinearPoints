import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FastCollinearPoints {
    private Point endpoint1;
    private Point endpoint2;
    private Double slopeEndPoint1;
    private Double slopeEndPoint2;
    private int numOfSegments;
    private LineSegment[] lineSegments;
    private Set<LineSegment> noDuplicateLines;

    /**
     * Examines an unconstrained number of euclidean points and checks whether they lie on the same line segment.
     * Does not include repeated subsegments.
     * @param points the euclidean points to be analyzed
     */
    public FastCollinearPoints(Point[] points) {
        Point[] copy = Arrays.copyOf(points, points.length);
        Point origin = new Point(0, 0);
        noDuplicateLines = new HashSet<LineSegment>();

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(copy, points[i].BY_SLOPE); //Time sort utilizes merge sort and insertion sort which is O(nlogn) which is perfect and it is stable
            Double invokingPointSlopeToOrigin = origin.slopeTo(points[i]);
            endpoint1 = points[i]; //Temporary storage
            endpoint2 = points[i]; //Temporary storage
            slopeEndPoint1 = invokingPointSlopeToOrigin;
            slopeEndPoint2 = invokingPointSlopeToOrigin;

            int right = 1; // we start at all non invoking points
            int left = 1;

            while (right + 1 < points.length) { // one less element in sorted array
                Double slope1 = points[i].slopeTo(copy[right]);
                Double slope2 = points[i].slopeTo(copy[right + 1]);

                if (slope1.equals(slope2)) {
                    Double slope1ToOrigin = origin.slopeTo(copy[right]);
                    Double slope2ToOrigin = origin.slopeTo(copy[right + 1]);

                    if (slope1ToOrigin > slopeEndPoint1) {
                        endpoint1 = copy[right];
                        slopeEndPoint1 = slope1ToOrigin;
                    }
                    if (slope2ToOrigin > slopeEndPoint1 && slope2ToOrigin > slope1ToOrigin) {
                        endpoint1 = copy[right + 1];
                        slopeEndPoint2 = slope2ToOrigin;
                    }
                    if (slope1ToOrigin < slopeEndPoint1) {
                        endpoint1 = copy[right];
                        slopeEndPoint1 = slope1ToOrigin;
                    }
                    if (slope2ToOrigin < slopeEndPoint1 && slope2ToOrigin < slope1ToOrigin) {
                        endpoint1 = copy[right + 1];
                        slopeEndPoint2 = slope2ToOrigin;
                    }

                } else {
                    if ((right - left + 1) >= 3) {
                        addLineSegmentToSet(noDuplicateLines,slopeEndPoint1,slopeEndPoint2,endpoint1,endpoint2);
                    }
                    left = right + 1;
                }
                right++;
            }
            if ((right - left + 1) >= 3) {
                addLineSegmentToSet(noDuplicateLines,slopeEndPoint1,slopeEndPoint2,endpoint1,endpoint2);
            }
        }
    }

    /**
     * Determines the number of distinct line segments composed of 4 or
     * more euclidean points that span a line
     * @return the number of distinct line segments
     */
    public int numberOfSegments() {
        return noDuplicateLines.size();
    }

    /**
     * Holds the distinct line segments that are composed of 4 or
     * more euclidean points
     * @return the distinct line segments
     */
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[this.numOfSegments];
        lineSegments = noDuplicateLines.toArray(new LineSegment[0]);

        return lineSegments;
    }

    /**
     *
     * @param set the set of distinct collinear line segments
     * @param slopeEndPoint1 the slope of the first endpoint of the collinear line
     * @param slopeEndPoint2 the slope of the second endpoint of the collinear line
     * @param endpoint1 the endpoint corresponding to the first slope
     * @param endpoint2 the endpoint corresponding to the second slope
     */
    private void addLineSegmentToSet(Set<LineSegment> set, Double slopeEndPoint1,
                                     Double slopeEndPoint2, Point endpoint1, Point endpoint2) {
        Point max = slopeEndPoint1 > slopeEndPoint2 ? endpoint1 : endpoint2;
        Point min = slopeEndPoint1 < slopeEndPoint2 ? endpoint1 : endpoint2;

        if (!min.equals(max)) {
            LineSegment line = new LineSegment(min, max);
            set.add(line);
        }
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
