package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.Audio;

import java.util.HashSet;


/**
 * The `Character` class represents a character in a game environment.
 * It includes methods to set the character's location, move the character,
 * and check if the character can move to a specified position.
 *
 * The character is associated with a specific style (TETile), a name, and is placed on a board.
 * It also defines walkable and obstacle tiles on the board.
 *
 * @author Kyaw Min Khant
  * @since Apr 15 2025
 */
public class Character {
    private TETile charStyle;
    private TETile charStyle2;
    private String name;
    private int myScore;
    private HashSet<Point> collectedCoinsPos;
    private Board board;
    private TETile[][] tiles;
    private Point location;
    private TETile walkableTile;
    private TETile obstacleTile;
    private TETile collectibleTile;


    /**
     * Constructs a new `Character` object with the specified style, board, initial position,
     * walkable tile, and obstacle tile.
     *
     * @param style          The visual style (TETile) of the character.
     * @param board          The game board on which the character is placed.
     * @param x              The initial x-coordinate of the character's position.
     * @param y              The initial y-coordinate of the character's position.
     * @param walkableTile   The tile on which the character can walk.
     * @param obstacleTile   The tile considered as an obstacle where the character cannot be created.
     */
    public Character(TETile style, TETile style2, Board board, int x, int y,
                     TETile walkableTile, TETile obstacleTile, TETile collectibleTile) {
        this.charStyle = style;
        this.charStyle2 = style2;
        this.myScore = 0;
        this.collectedCoinsPos = new HashSet<>();
        this.name = "Player 1";
        this.board = board;
        this.tiles = board.getTiles();
        this.location = new Point(x, y);
        this.walkableTile = walkableTile;
        this.obstacleTile = obstacleTile;
        this.collectibleTile = collectibleTile;

        if (tiles[x][y] != this.obstacleTile) {
            this.board.setTiles(x, y, style);
        } else {
            System.out.println("cannot create character on " + this.obstacleTile.description());
        }
    }

    public String getName() {
        return name;
    }

    public void addCollectible(Point coin) {
        this.collectedCoinsPos.add(coin);
    }

    public void updateMyScore(int score) {
        this.myScore = score;
    }

    public int getMyScore() {
        return myScore;
    }

    public HashSet<Point> getCollectedCoinPositions() {
        return collectedCoinsPos;
    }


    /**
     * Sets the location of the character to the specified coordinates.
     *
     * @param x The x-coordinate of the new location.
     * @param y The y-coordinate of the new location.
     */
    public void setLocation(int x, int y) {
        location.setX(x);
        location.setY(y);
    }

    /**
     * Moves the character by the specified deltas in the x and y directions.
     *
     * @param deltaX The change in the x-coordinate for the character's movement.
     * @param deltaY The change in the y-coordinate for the character's movement.
     */
    public void move(int deltaX, int deltaY) {
        if (!canMove(deltaX, deltaY)) {
            Audio.playAudio("collision.wav");
            tiles[location.x()][location.y()] = charStyle2;
            System.out.println("Error: move error");
            return;
        }

        // swap tiles to move
        // Audio.playAudio("sound.wav");

        int newPosX = location.x() + deltaX;
        int newPosY = location.y() + deltaY;

        tiles[location.x()][location.y()] = walkableTile;
        tiles[newPosX][newPosY] = charStyle;

        // move current position
        location.setX(newPosX);
        location.setY(newPosY);
    }

    /**
     * Gets the current location of the character.
     *
     * @return The current location as a Point object.
     */
    public Point getLocation() {
        return location;
    }

    /**
     * Checks if the character can move to the specified position based on bounds and obstacles.
     *
     * @param deltaX The change in the x-coordinate for the potential movement.
     * @param deltaY The change in the y-coordinate for the potential movement.
     * @return True if the character can move to the specified position, false otherwise.
     */
    private boolean canMove(int deltaX, int deltaY) {
        int newPosX = location.x() + deltaX;
        int newPosY = location.y() + deltaY;

        // check bounds
        if (newPosX >= RoomPlot.BOARD_LENGTH || newPosX < 0
            || newPosY >= RoomPlot.BOARD_HEIGHT || newPosY < 0) {
            System.out.println("Cannot walk outside of board");
            return false;
        }

        // check collectible
        if (tiles[newPosX][newPosY] == Tileset.COIN_ICON) {
            collectedCoinsPos.add(new Point(newPosX, newPosY));
            myScore++;
            Audio.playAudio("coin.wav");
            System.out.println(this.name + " current Score is: " + myScore);
            return true;
        }

        // check walls
        if (tiles[newPosX][newPosY] != walkableTile) {
            System.out.println("Cannot walk on " + tiles[newPosX][newPosY].description());
            return false;
        }
        return true;
    }
}
