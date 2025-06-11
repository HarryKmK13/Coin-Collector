package core;

import tileengine.TETile;


/**
 * The Theme class represents a theme for the game, defining the tiles
 * for the wall, floor, and background of the game board.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class Theme {
    private TETile WALL;
    private TETile FLOOR;
    private TETile BACKGROUND;
    private TETile CHARACTER;
    private TETile CHARACTERTWO;

    /**
     * Constructs a new Theme with specified tiles for the wall, floor, and background.
     *
     * @param wall       The tile representing the wall in the game.
     * @param floor      The tile representing the floor in the game.
     * @param background The tile representing the background of the game board.
     */
    public Theme(TETile wall, TETile floor, TETile background, TETile character, TETile character2) {
        this.WALL = wall;
        this.FLOOR = floor;
        this.BACKGROUND = background;
        this.CHARACTER = character;
        this.CHARACTERTWO = character2;
    }

    /**
     * Retrieves the tile representing the wall.
     *
     * @return The tile representing the wall.
     */
    public TETile getWall() {
        return WALL;
    }

    /**
     * Retrieves the tile representing the floor.
     *
     * @return The tile representing the floor.
     */
    public TETile getFloor() {
        return FLOOR;
    }

    /**
     * Retrieves the tile representing the background of the game board.
     *
     * @return The tile representing the background.
     */
    public TETile getBackground() {
        return BACKGROUND;
    }

    public TETile getCharacter() {
        return CHARACTER;
    }

    public TETile getCharacter2() {
        return CHARACTERTWO;
    }
}
