import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point
    public final Comparator<Point> BY_SLOPE = new BySlope(); //One Comparator per Ordering method (by slope for this comparator)

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        else if (this.y == that.y) return 0.0;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0; // they are the same points
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return BY_SLOPE;
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point firstPoint, Point secondPoint) {
            Point that = new Point(x, y);
            Double firstToInvokingSlope = firstPoint.slopeTo(that);
            Double secondToInvokingSlope = secondPoint.slopeTo(that);

            return Double.compare(firstToInvokingSlope,secondToInvokingSlope);
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point point1 = new Point(1, 2);
        Point point2 = new Point(2, 3);

        Point point3 = new Point(2, 2);
        Point point4 = new Point(2, 3);

        Point point5 = new Point(2, 2);
        Point point6 = new Point(2, 2);

        Point point7 = new Point(3, 2);
        Point point8 = new Point(2, 2);

        System.out.println("Testing slopeTo method of a finite slope length");
        System.out.println(point1.slopeTo(point2));

        System.out.println("Testing slopeTo method of an infinite slope length");
        System.out.println(point3.slopeTo(point4));

        System.out.println("Testing slopeTo method of an negative infinite slope length - points are the same");
        System.out.println(point5.slopeTo(point6));

        System.out.println("Testing slopeTo method of a 0 slope length");
        System.out.println(point7.slopeTo(point8));

        System.out.println("Testing Comparator comparing by slope - positive number if point 1 is further from invoking point and negative if closer and 0 if the same");
        System.out.println(point1.BY_SLOPE.compare(point2,point3));
        System.out.println(point1.BY_SLOPE.compare(point3,point2));
        System.out.println(point1.BY_SLOPE.compare(point2,point2));

        System.out.println("Testing slope Order method without passing in comparator instance and just calling slopeOrder method");
        System.out.println(point1.slopeOrder().compare(point1,point2));


    }
}