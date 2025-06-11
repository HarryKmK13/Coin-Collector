package core;


import java.util.Random;

/**
 * The RoomGenerator class is responsible for generating random parameters
 * for rooms and hallways in the game board.
 *
 * @author Kyaw Min Khant
  * @since Apr 15 2025
 */
public class RoomGenerator {
    public static final int MIN_ROOM_SIZE = 6;
    public static final int MAX_ROOM_SIZE = 13;
    public static final int MIN_ITER = 1000;
    public static final int MAX_ITER = 1500;
    private Random rand;

    /**
     * Constructs a new RoomGenerator with the specified seed.
     *
     * @param seed The seed for the random number generator.
     */
    public RoomGenerator(long seed) {
        this.rand = new Random(seed);
    }

    /**
     * Generates a random starting x-coordinate for a room.
     *
     * @return A random starting x-coordinate.
     */

    public int getRandomStartX() {
        return rand.nextInt(0, RoomPlot.BOARD_LENGTH);
    }

    /**
     * Generates a random starting y-coordinate for a room.
     *
     * @return A random starting y-coordinate.
     */
    public int getRandomStartY() {
        return rand.nextInt(0, RoomPlot.BOARD_HEIGHT);
    }

    /**
     * Generates a random length for a room.
     *
     * @return A random length for a room.
     */
    public int getRandomLength() {
        return rand.nextInt(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
    }

    /**
     * Generates a random height for a room.
     *
     * @return A random height for a room.
     */
    public int getRandomHeight() {
        return rand.nextInt(MIN_ROOM_SIZE, MAX_ROOM_SIZE);
    }

    /**
     * Generates the number of trails for rooms to connect on the board.
     *
     * @return The number of trails for rooms.
     */
    public int getTrailsForRooms() {
        return rand.nextInt(MIN_ITER, MAX_ITER);
    }

    /**
     * Generates a random position for a hallway between two points of two different rooms.
     *
     * @param start The starting point of the hallway.
     * @param end   The ending point of the hallway.
     * @return A random position for a hallway between the start and end points.
     */
    public int getRandomHallwayPosition(int start, int end) {
        if (start < end) {
            return rand.nextInt(start, end - 2);
        } else {
            return rand.nextInt(end, start - 2);
        }
    }

    public int getInBetween(int start, int end) {
        return rand.nextInt(start, end);
    }

    /**
     * Generates a random integer up to (but not including) the specified number.
     * The returned value is in the range [0, number).
     *
     * @param number The upper bound (exclusive) for the generated random number.
     * @return A random integer in the range [0, number).
     */
    public int getRandomUpTo(int number) {
        return rand.nextInt(number);
    }
}
