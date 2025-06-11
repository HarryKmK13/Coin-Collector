package core;

import tileengine.TETile;
import tileengine.Tileset;

/**
 * The World class represents the game world and operates the TETile board.
 * It generates rooms, hallways, and provides access to the game board.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class World {
    private TETile[][] board;
    private Character character;
    private Character enemy;
    private Theme theme;
    private RoomPlot plotter;
    private Board testBoard;
    private int totalNumCoins;
    private static final TETile COLLECTIBLE = Tileset.COIN_ICON;

    /**
     * Constructs a new World instance with a specified seed.
     *
     * @param seed The seed for generating random elements in the world.
     */
    public World(Long seed, Theme theme) {
        this.theme = theme;

        testBoard = new Board(theme);
        plotter = new RoomPlot(testBoard, seed, theme);
        plotter.plotRooms();
        plotter.createHallways();
        this.totalNumCoins = plotter.getRandomNumberOfCoins();
        Point charLocation = plotter.getCharacterLocation();
        this.character = new Character(theme.getCharacter(), theme.getCharacter2(),
                testBoard, charLocation.x(), charLocation.y(), theme.getFloor(),
                theme.getWall(), COLLECTIBLE);
        this.dropCoins(totalNumCoins);
        this.board = testBoard.getTiles();
    }

    public int getTotalNumCoins() {
        return totalNumCoins;
    }

    private void dropCoins(int count) {
        int i = 0;
        while (i < count) {
            Point coinLocation = plotter.getRandomCoinLocation();
            if (testBoard.getTiles()[coinLocation.x()][coinLocation.y()] == theme.getFloor()) {
                testBoard.getTiles()[coinLocation.x()][coinLocation.y()] = COLLECTIBLE;
                i++;
            }
        }
    }

    /**
     * Gets the TETile board representing the game world that has rooms, hallways and a character.
     *
     * @return The TETile board.
     */
    public TETile[][] getTiles() {
        return board;
    }

    public Character getCharacter() {
        return character;
    }

    /**
     * Modify the location of the character in the world. This method swaps the tile of old character
     * location and that of new character location.
     *
     * @param x the new x position of the character
     * @param y the new y position of the character
     */
    public void modifyCharacterLocation(int x, int y) {
        this.getTiles()[character.getLocation().x()][character.getLocation().y()] = theme.getFloor();
        this.getTiles()[x][y] = theme.getCharacter();
        this.character.setLocation(x, y);
    }
}
