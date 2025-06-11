package core;

import java.util.Random;

/**
 * The Room class represents a rectangular room in the game.
 * It provides methods to retrieve information about the room.
 * It implements Comparable to allow sorting based on the middle point.
 *
 * @author Kyaw Min Khant
 *  @since Apr 15 2025
 */
public class Room implements Comparable<Room> {
    private int x;
    private int y;
    private int xLen;
    private int yLen;
    private Random rand;

    /**
     * Constructs a new Room with the specified parameters.
     *
     * @param startPosX The starting x-coordinate of the room.
     * @param startPosY The starting y-coordinate of the room.
     * @param length    The length of the room.
     * @param height    The height of the room.
     */
    public Room(int startPosX, int startPosY, int length, int height, long seed) {
        this.x = startPosX;
        this.y = startPosY;
        this.xLen = length;
        this.yLen = height;
        this.rand = new Random(seed);
    }

    /**
     * Gets the starting x-coordinate of the room.
     *
     * @return The starting x-coordinate.
     */
    public int getStartX() {
        return this.x;
    }

    /**
     * Gets the starting y-coordinate of the room.
     *
     * @return The starting y-coordinate.
     */
    public int getStartY() {
        return this.y;
    }

    /**
     * Gets the length of the room.
     *
     * @return The length of the room.
     */
    public int getLength() {
        return this.xLen;
    }

    /**
     * Gets the height of the room.
     *
     * @return The height of the room.
     */
    public int getHeight() {
        return this.yLen;
    }

    /**
     * Gets the middle point of the room.
     *
     * @return The middle point of the room.
     */
    public Point getMiddle() {
        int middleX = x + xLen / 2;
        int middleY = y + yLen / 2;
        return new Point(middleX, middleY);
    }

    public Point getRandomPositionInRoom() {
        int randomX = rand.nextInt(getStartX(), getStartX() + getLength() - 1);
        int randomY = rand.nextInt(getStartY(), getStartY() + getHeight() - 1);
        return new Point(randomX, randomY);
    }

    /**
     * Calculates the Euclidean distance between the middle points of two rooms.
     *
     * @param o The other room.
     * @return The Euclidean distance between the middle points.
     */
    public double distanceTo(Room o) {
        int x1 = this.getMiddle().x();
        int y1 = this.getMiddle().y();
        int x2 = o.getMiddle().x();
        int y2 = o.getMiddle().y();
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    /**
     * Compares two rooms based on their x-coordinate of the middle point.
     *
     * @param o The other room to compare with.
     * @return A negative integer, zero, or a positive integer as this room
     *         is less than, equal to, or greater than the specified room.
     */
    @Override
    public int compareTo(Room o) {
        return Integer.compare(this.getMiddle().x(), o.getMiddle().x());
    }

    /**
     * Returns a string representation of the room, showing its middle point.
     *
     * @return A string representation of the room.
     */
    @Override
    public String toString() {
        return "Room Middle: (" + this.getMiddle().x() + ", " + this.getMiddle().y() + ")";
    }
}
