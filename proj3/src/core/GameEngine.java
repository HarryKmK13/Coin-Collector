package core;

import tileengine.TERenderer;
import utils.SnapshotUtils;

import java.util.HashSet;
import java.util.Random;

/**
 * The `GameEngine` class initializes and executes the game,
 * allowing players to either start a new game or continue a saved one.
 *
 * It interacts with the user through a menu system, retrieves the game seed,
 * and handles loading saved games from a specified file path.
 *
 * @author Kyaw Min Khant
  * @since Apr 15 2025
 */
public class GameEngine {
    private Theme[] themes;
    private Theme theme;
    private TERenderer ter;
    private long seed;
    private Random rand;
    final int DEFAULT_TIMER = 60;       // default is 60 seconds

    /**
     * Constructs a new `GameEngine` object with the specified renderer and theme.
     *
     * @param ter   The renderer for displaying the game world.
     * @param themes The theme defining the appearance of tiles and characters.
     */
    public GameEngine(TERenderer ter, Theme[] themes) {
        this.ter = ter;
        this.themes = themes;
    }

    /**
     * Initializes and runs the game based on user input, allowing for starting a new game
     * or continuing a saved game.
     */
    public void initialize() {
        Menu menu = new Menu(ter);
        this.seed = menu.run();
        System.out.println("received seed: " + seed);

        if (seed < 0) {
            // Load snapshot of saved game
            Snapshot snapshot;
            snapshot = SnapshotUtils.loadSeed();

            seed = snapshot.getSeed();
            rand = new Random(this.seed);
            this.theme = themes[rand.nextInt(themes.length)]; // bound exclusive
            int x = snapshot.getCharLocation().x();
            int y = snapshot.getCharLocation().y();
            int score = snapshot.getScore();
            double timeLeft = snapshot.getTimeLeft();

            HashSet<Point> collectedLocations = snapshot.getCollectibles();

            Game game = new Game(seed, ter, timeLeft, theme);
            game.initialize();
            for (Point point : collectedLocations) {
                game.getCharacter().addCollectible(point);
                game.hideCollectibleTile(point.x(), point.y());
            }
            game.getCharacter().updateMyScore(score);
            game.modifyCharacterLocation(x, y);
            game.run();
        } else {
            // Start a new game
            rand = new Random(this.seed);
            this.theme = themes[rand.nextInt(themes.length)]; // bound exclusive
            Game game = new Game(seed, ter, DEFAULT_TIMER, theme);
            game.initialize();
            game.run();
        }
    }
}
