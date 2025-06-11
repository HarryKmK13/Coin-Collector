package core;

/**
 * The Point class represents a point with x and y coordinates.
 * It provides methods to retrieve the coordinates and a string representation.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class Point {
    private int x;
    private int y;

    /**
     * Constructs a new Point with the specified x and y coordinates.
     *
     * @param x The x-coordinate of the point.
     * @param y The y-coordinate of the point.
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    /**
     * Gets the x-coordinate of the point.
     *
     * @return The x-coordinate of the point.
     */
    public int x() {
        return this.x;
    }

    /**
     * Gets the y-coordinate of the point.
     *
     * @return The y-coordinate of the point.
     */
    public int y() {
        return this.y;
    }

    /**
     * Returns a string representation of the point.
     *
     * @return A string representation of the point.
     */
    @Override
    public String toString() {
        return "Point (" + this.x + ", " + this.y + ")";
    }
}
