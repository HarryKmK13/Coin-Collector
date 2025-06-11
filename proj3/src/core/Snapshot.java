package core;

import java.util.HashSet;

/**
 * The `Snapshot` class represents a snapshot of the game state, capturing key elements
 * such as the game seed, character location, and optional flower locations.
 *
 * It provides methods to retrieve information about the game state stored in the snapshot.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class Snapshot {
    private long seed;
    private int playerScore;
    private HashSet<Point> collectibles;
    private Point charLocation;
    private double timeLeft;

    /**
     * Constructs a new `Snapshot` object with the specified seed and character location.
     *
     * @param seed          The seed for generating the game world.
     * @param charLocation  The location of the main character in the game.
     */
    public Snapshot(long seed, Point charLocation, int playerScore, double timeLeft) {
        this.seed = seed;
        this.charLocation = charLocation;
        this.playerScore = playerScore;
        this.timeLeft = timeLeft;
        this.collectibles = new HashSet<>();
    }

    public void addCollectible(Point point) {
        this.collectibles.add(point);
    }

    /**
     * Gets the seed stored in the snapshot.
     *
     * @return The seed value.
     */
    public long getSeed() {
        return this.seed;
    }

    /**
     * Gets the character location stored in the snapshot.
     *
     * @return The character location as a Point object.
     */
    public Point getCharLocation() {
        return charLocation;
    }

    public int getScore() {
        return playerScore;
    }

    public double getTimeLeft() {
        return this.timeLeft;
    }

    public HashSet<Point> getCollectibles() {
        return this.collectibles;
    }
}
