import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
//problem fix the stupid hashcode and HashSet I'm using maybe read MIT COURSE on that :(((
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

    // finds all line segments containing 4 or more points
    // Use mergeSort algorithm and comparator object to run in time complexity of n^2log(n) max time and space proportional to O(n) so just don't use O(m)
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

            while (right < points.length) { // one less element in sorted array

                if (points[i].slopeTo(copy[right]) == points[i].slopeTo(copy[right + 1])) {
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
                    if ((right - left + 1) >= 3 && copy[right + 1] != copy[right]) {
                        Point max = slopeEndPoint1 > slopeEndPoint2 ? endpoint1 : endpoint2;
                        Point min = slopeEndPoint1 < slopeEndPoint2 ? endpoint1 : endpoint2;

                        LineSegment line = new LineSegment(min, max);
                        noDuplicateLines.add(line);
                    }
                    left = right + 1;
                }

                right++;
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return noDuplicateLines.size();
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[this.numOfSegments];
        lineSegments = noDuplicateLines.toArray(new LineSegment[0]);

        return lineSegments;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
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
