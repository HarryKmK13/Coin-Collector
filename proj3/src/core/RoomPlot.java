package core;

import tileengine.TETile;
import java.util.HashSet;
import java.util.Stack;


/**
 * The RoomPlot class is a main file responsible for generating and plotting rooms on the game board.
 * It also connects the rooms by creating hallways between them.
 * It uses a random seed for room generation and a specified theme for the tiles.
 *
 * @author Kyaw Min Khant
  * @since Apr 15 2025
 */
public class RoomPlot {
    public static final int BOARD_LENGTH = 60;
    public static final int BOARD_HEIGHT = 40;
    private long seed;
    private int trials;
    private TETile wall;
    private TETile background;
    private TETile floor;
    private Board board;
    private RoomGenerator rGen;
    private Stack<Room> connectedStack;
    private HashSet<Room> connectedSet;


    /**
     * Constructs a new RoomPlot with the specified board, seed, and theme.
     *
     * @param board The game board where rooms will be plotted.
     * @param seed  The seed for generating random elements in the world.
     * @param theme The theme specifying the tiles for walls, floors, and background.
     */
    public RoomPlot(Board board, long seed, Theme theme) {
        rGen = new RoomGenerator(seed);
        this.seed = seed;
        this.trials = rGen.getTrailsForRooms();
        this.board = board;
        this.wall = theme.getWall();
        this.background = theme.getBackground();
        this.floor = theme.getFloor();
        this.connectedStack = new Stack<>();
        this.connectedSet = new HashSet<>();
    }

    /**
     * Generates and plots random rooms on the game board.
     */
    public void plotRooms() {
        int count = 0;
        Room currRoom;
        while (count < trials) {
            currRoom = generateRandomRoom();
            if (!isCollision(currRoom)) {
                drawRoom(currRoom);
                board.addRoom(currRoom);
            }
            count++;
        }
    }


    public int getRandomNumberOfCoins() {
        return rGen.getInBetween(5, board.getRooms().size());
    }

    public Point getCharacterLocation() {
        Room randomRoom = pickRandomRoom();
        return randomRoom.getMiddle();
    }

    public Point getRandomCoinLocation() {
        Room randomRoom = pickRandomRoom();
        return randomRoom.getRandomPositionInRoom();
    }

    private Room pickRandomRoom() {
        int roomIndex = rGen.getRandomUpTo(board.getRooms().size());
        return board.getRooms().get(roomIndex);
    }


    /**
     * Creates hallways to connect all rooms on the game board.
     * The method connects rooms by finding the closest
     * unconnected neighbor for each room. It populates the connectedStack and
     * connectedSet to keep track of the connected rooms.
     * The method guarantees that the rooms are at least all-connected as a tree.
     */
    public void createHallways() {
        connectedStack.push(board.getRooms().get(0));      // added the most left room to connectedSet
        connectedSet.add(board.getRooms().get(0));

        Room closetNeighbor = null;
        for (int i = 0; i < board.getRooms().size() - 1; i++) {
            Room lastConnectedRoom = connectedStack.pop();
            double shortestDistance = Double.POSITIVE_INFINITY;
            for (int index = 0; index < board.getRooms().size(); index++) {
                Room newRoom = board.getRooms().get(index);
                if (!connectedSet.contains(newRoom)) {
                    double distance = lastConnectedRoom.distanceTo(newRoom);
                    if (distance < shortestDistance) {
                        shortestDistance = distance;
                        closetNeighbor = board.getRooms().remove(index);
                    }
                }
            }
            drawHallway(lastConnectedRoom, closetNeighbor);
            connectedStack.push(closetNeighbor);
            connectedSet.add(closetNeighbor);
        }
    }

    /**
     * Draws a virtual room on the game board for hallway creation.
     * The method iterates through the coordinates of the virtual room
     * and sets the tiles on the board to represent the virtual room,
     * which is used for connecting hallways between two rooms that cannot be
     * connected in a Manhattan Geometry.
     * Additionally, it ensures that the corners of the virtual room
     * are properly handled to maintain the hallway structure.
     *
     * @param room The virtual room for which tiles are being drawn.
     */
    private void virtualRoomDrawer(Room room) {
        for (int i = room.getStartX(); i < room.getStartX() + room.getLength(); i++) {
            for (int j = room.getStartY(); j < room.getStartY() + room.getHeight(); j++) {
                if (validIndex(i, j)) {
                    if ((board.getTiles()[i][j] != floor)  && (j == room.getStartY()
                            || j == room.getStartY() + room.getHeight() - 1
                            || i == room.getStartX() || i == room.getStartX() + room.getLength() - 1)) {
                        board.setTiles(i, j, wall);
                    } else {
                        board.setTiles(i, j, floor);
                    }
                }
            }
        }
        board.setTiles(room.getStartX() + 1, room.getStartY() + 1, floor);
    }

    /**
     * Draws a room on the game board with specified wall and floor tiles.
     * The method iterates through the coordinates of the room and sets
     * the tiles on the board to represent the walls and floors of the room.
     * It ensures that the corners of the room are properly handled to
     * maintain the room structure.
     *
     * @param room The room to be drawn on the game board.
     */
    private void drawRoom(Room room) {
        for (int i = room.getStartX(); i < room.getStartX() + room.getLength(); i++) {
            for (int j = room.getStartY(); j < room.getStartY() + room.getHeight(); j++) {
                if (validIndex(i, j)) {
                    if (j == room.getStartY() || j == room.getStartY() + room.getHeight() - 1
                            || i == room.getStartX() || i == room.getStartX() + room.getLength() - 1) {
                        board.setTiles(i, j, wall);
                    } else {
                        board.setTiles(i, j, floor);
                    }
                }
            }
        }
    }

    /**
     * Draws a hallway connecting two rooms on the game board.
     * The method assumes that the starting coordinates of the first room (r1)
     * are less than the starting coordinates of the second room (r2).
     * It handles both horizontal, vertical hallways, and crossing hallways,
     * ensuring that a * connection is established between the two rooms
     * on the board.
     *
     * @param r1 The first room to be connected.
     * @param r2 The second room to be connected.
     */
    private void drawHallway(Room r1, Room r2) {
        // swap rooms to handle + and - directions
        if (r1.getStartX() > r2.getStartX() || r1.getStartY() > r2.getStartY()) {
            Room temp;
            temp = r1;
            r1 = r2;
            r2 = temp;
        }

        Point xOverlapPt = findOverlap(r1.getStartX(), r1.getStartX() + r1.getLength(),
                r2.getStartX(), r2.getStartX() + r2.getLength());

        if (xOverlapPt == null) {
            Point yOverlapPt = findOverlap(r1.getStartY(), r1.getStartY() + r1.getHeight(),
                    r2.getStartY(), r2.getStartY() + r2.getHeight());

            if (yOverlapPt == null) {
                Room virtualRoom = new Room(r2.getStartX(), r1.getStartY(), 3, 3, seed);
                virtualRoomDrawer(virtualRoom);
                drawHallway(r1, virtualRoom);
                drawHallway(virtualRoom, r2);
            } else {
                // tunneling X in a random HallwayIndex
                int start = yOverlapPt.x();
                int end = yOverlapPt.y();
                tunnelX(start, end, r1, r2);
            }
        } else {
            // tunneling Y in a random HallwayIndex
            int start = xOverlapPt.x();
            int end = xOverlapPt.y();
            tunnelY(start, end, r1, r2);
        }
    }

    /**
     * Creates a horizontal tunnel (hallway) between two rooms on the game board.
     * The method determines the appropriate start and end points for the tunnel
     * and draws the hallway tiles between the specified rooms. It uses a virtual
     * room to handle cases where the overlap length is less than 3 tiles. If the
     * overlap is less than 3, it's not possible to connect the two rooms with just
     * one horizontal hallway. This obstacle is overcome using a virtual room.
     *
     * @param start The starting index for the tunnel on the Y-axis.
     * @param end   The ending index for the tunnel on the Y-axis.
     * @param r1    The first room.
     * @param r2    The second room.
     */
    private void tunnelX(int start, int end, Room r1, Room r2) {
        if (end - start < 3) {
            Room virtualRoom = new Room(r2.getStartX(), r1.getStartY(),
                    3, 3, seed);
            virtualRoomDrawer(virtualRoom);
            drawHallway(r1, virtualRoom);
            drawHallway(virtualRoom, r2);
            return;
        }

        int hallIndex;
        hallIndex = rGen.getRandomHallwayPosition(start + 1, end + 1);
        if (r2.getStartX() < r1.getStartX()) {
            Room temp = r1;
            r1 = r2;
            r2 = temp;
        }

        for (int i = r1.getStartX() + r1.getLength() - 1; i < r2.getStartX() + 1; i++) {
            if (board.getTiles()[i][hallIndex] == wall
                    || board.getTiles()[i][hallIndex] == background
                    || board.getTiles()[i][hallIndex] == wall) {
                board.setTiles(i, hallIndex, floor);
                if (board.getTiles()[i][hallIndex - 1] != floor) {
                    board.setTiles(i, hallIndex - 1, wall);
                }
                if (board.getTiles()[i][hallIndex + 1] != floor) {
                    board.setTiles(i, hallIndex + 1, wall);
                }
            }
        }
    }

    /**
     * Creates a vertical tunnel (hallway) between two rooms on the game board.
     * The method determines the appropriate start and end points for the tunnel
     * and draws the hallway tiles between the specified rooms. It uses a virtual
     * room to handle cases where the overlap length is less than 3 tiles. If the
     * overlap is less than 3, it's not possible to connect the two rooms with just
     * one vertical hallway. This obstacle is overcome using a virtual room.
     *
     * @param start The starting index for the tunnel on the Y-axis.
     * @param end   The ending index for the tunnel on the Y-axis.
     * @param r1    The first room.
     * @param r2    The second room.
     */
    private void tunnelY(int start, int end, Room r1, Room r2) {
        if (end - start < 3) {
            int index = r1.getStartX();
            Room virtualRoom = new Room(index, r2.getStartY(), 3, 3, seed);
            virtualRoomDrawer(virtualRoom);
            drawHallway(r1, virtualRoom);
            drawHallway(virtualRoom, r2);
            return;
        }

        int hallIndex;
        hallIndex = rGen.getRandomHallwayPosition(start + 1, end + 1);
        if (r2.getStartY() < r1.getStartY()) {
            Room temp = r1;
            r1 = r2;
            r2 = temp;
        }
        for (int i = r1.getStartY() + r1.getHeight() - 1; i < r2.getStartY() + 1; i++) {
            if (board.getTiles()[hallIndex][i] == wall
                    || board.getTiles()[hallIndex][i] == background) {
                board.setTiles(hallIndex, i, floor);
                if (board.getTiles()[hallIndex - 1][i] != floor) {
                    board.setTiles(hallIndex - 1, i, wall);
                }
                if (board.getTiles()[hallIndex + 1][i] != floor) {
                    board.setTiles(hallIndex + 1, i, wall);
                }
            }
        }
    }

    /**
     * Finds the overlap range between two intervals.
     * The method checks for the existence of an overlap between the
     * specified intervals and calculates the start and end points of
     * the overlap if it exists.
     *
     * @param start1 The starting index of the first interval on the X-axis.
     * @param end1   The ending index of the first interval on the X-axis.
     * @param start2 The starting index of the second interval on the X-axis.
     * @param end2   The ending index of the second interval on the X-axis.
     * @return A Point object representing the start and end points of the overlap,
     *         or null if there is no overlap.
     */
    private Point findOverlap(int start1, int end1, int start2, int end2) {
        // Check for no overlap
        if (end1 < start2 || end2 < start1) {
            return null;
        }

        // Calculate the overlap
        int overlapStart = Math.max(start1, start2);
        int overlapEnd = Math.min(end1, end2);

        // overlap is from start to end-1
        return new Point(overlapStart, overlapEnd);
    }

    /**
     * Checks for collisions between a room and the game board boundaries or other existing rooms.
     * The method evaluates whether the specified room extends beyond the game board boundaries
     * or overlaps with tiles occupied by other rooms on the board.
     *
     * @param room The room to be checked for collisions.
     * @return True if a collision is detected, indicating that the room cannot be placed;
     *         otherwise, false if the room can be placed without collision.
     */
    private boolean isCollision(Room room) {
        // Collision with boundaries
        if (room.getStartX() + room.getLength() >= BOARD_LENGTH
                || room.getStartY() + room.getHeight() >= BOARD_HEIGHT) {
            return true;
        }
        // Collision with other existing rooms
        for (int i = room.getStartX() - 1; i < room.getStartX() + room.getLength() + 1; i++) {
            for (int j = room.getStartY() - 1; j < room.getStartY() + room.getHeight() + 1; j++) {
                if (validIndex(i, j) && (board.getTiles()[i][j] == floor
                        || board.getTiles()[i][j] == wall)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the specified indices are within the valid range of the game board.
     * The method determines whether the given indices fall within the acceptable
     * range for the X and Y axes of the game board.
     *
     * @param x The index on the X-axis to be checked.
     * @param y The index on the Y-axis to be checked.
     * @return True if the indices are within the valid range; otherwise, false.
     */
    private boolean validIndex(int x, int y) {
        return x >= 0 && x < BOARD_LENGTH && y >= 0 && y < BOARD_HEIGHT;
    }

    /**
     * Generates a random room with random starting coordinates, length, and height.
     * The method utilizes the RoomGenerator to obtain random values for the starting
     * coordinates (X and Y), the length, and the height of the room.
     *
     * @return A randomly generated room with random starting coordinates, length, and height.
     */
    private Room generateRandomRoom() {
        int x = rGen.getRandomStartX();
        int y = rGen.getRandomStartY();
        int length = rGen.getRandomLength();
        int height = rGen.getRandomHeight();
        return new Room(x, y, length, height, seed);
    }
}
