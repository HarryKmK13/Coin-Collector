package core;

import tileengine.TETile;
import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The Board class represents the game board, which consists of tiles,
 * rooms, and other game elements.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class Board {
    private TETile[][] board;
    private PriorityQueue<Room> rooms;
    private TETile background;

    /**
     * Constructs a new Board with a specified theme.
     *
     * @param theme The theme used for the game board.
     */
    public Board(Theme theme) {
        this.board = new TETile[RoomPlot.BOARD_LENGTH][RoomPlot.BOARD_HEIGHT];
        this.background = theme.getBackground();
        clearBoard();
        this.rooms = new PriorityQueue<>();
    }

    /**
     * Clears the game board by setting all tiles to the background tiles.
     */
    private void clearBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = background;
            }
        }
    }

    /**
     * Adds a room to the game board.
     *
     * @param r The room to be added to the board.
     */
    public void addRoom(Room r) {
        this.rooms.add(r);
    }

    /**
     * Retrieves a list of rooms on the game board.
     *
     * @return An ArrayList containing the rooms on the board.
     */
    public ArrayList<Room> getRooms() {
        ArrayList<Room> list = new ArrayList<>();
        for (Room room : this.rooms) {
            list.add(room);
        }
        return list;
    }

    /**
     * Sets the tile type at a specific position on the game board.
     *
     * @param x         The x-coordinate of the position.
     * @param y         The y-coordinate of the position.
     * @param tileType  The type of tile to set at the position.
     */
    public void setTiles(int x, int y, TETile tileType) {
        this.board[x][y] = tileType;
    }

    /**
     * Retrieves the 2D array of tiles representing the game board.
     *
     * @return The 2D array of tiles.
     */
    public TETile[][] getTiles() {
        return this.board;
    }
}
