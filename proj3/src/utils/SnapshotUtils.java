package utils;

import core.Character;
import core.Point;
import core.Snapshot;

import java.util.HashSet;

/**
 * The `SnapshotUtils` class provides utility methods for saving and loading game snapshots.
 * It includes functionality to save the game seed and character location to a file
 * and to load a game snapshot from a file.
 *
 * The file path for saving and loading snapshots is defined as a constant within the class.
 *
 * @author Wai Han, Pranay Mallik
 * @version 1.2
 * @since Nov 17, 2023
 */
public class SnapshotUtils {
    private static final String FILEPATH = "save-file.txt";

    /**
     * Saves the game seed and character location to a file.
     *
     * @param seed      The seed for generating the game world.
     * @param character The main character of the game.
     */
    public static void saveSeed(long seed, Character character, double remainingSeconds) {
        String content = seed + "\n" + character.getMyScore() + "\n" + remainingSeconds + "\n"
                + character.getLocation().x() + "," + character.getLocation().y() + "\n";

        for (Point coin : character.getCollectedCoinPositions()) {
//            if (coin.x() == character.getLocation().x() && coin.y() == character.getLocation().y()) {
//                continue;
//            }
            content += coin.x() + "," +coin.y() + "\n";
        }
        FileUtils.writeFile(FILEPATH, content);
    }

    /**
     * Loads a game snapshot from a file.
     *
     * @return A `Snapshot` object representing the loaded game state, or null if the file does not exist.
     */
    public static Snapshot loadSeed() {
        if (!FileUtils.fileExists(FILEPATH)) {
            System.out.println(FILEPATH + " does not exist");
            return null;
        }
        System.out.println("The line starts here");
        String lines = FileUtils.readFile(FILEPATH);

        String[] line = lines.split("\n");
        long seed = Integer.parseInt(line[0]);
        int score = Integer.parseInt(line[1]);
        double timeLeft = Double.parseDouble(line[2]);
        String[] characterLoc = line[3].split(",");
        Point charLocation = new Point(Integer.parseInt(characterLoc[0]), Integer.parseInt(characterLoc[1]));

        Snapshot ss = new Snapshot(seed, charLocation, score, timeLeft);

        for (int i = 3; i < line.length; i++) {
            String[] parts = line[i].split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            Point location = new Point(x, y);
            ss.addCollectible(location);
        }
        return ss;
    }
}
