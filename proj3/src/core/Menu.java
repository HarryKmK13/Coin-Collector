package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import utils.FileUtils;

import java.awt.*;


/**
 * The `Menu` class provides a menu system for starting a new game, loading a game, or quitting the application.
 * It interacts with the user through StdDraw to capture input and display menu options.
 *
 * The menu allows the user to enter a seed for generating a new game or loading an existing game.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class Menu {
    private TERenderer ter;
    char key;
    private long prevFrameTimestamp;
    private boolean handleSeed;
    private boolean isLoading;
    private boolean savedGameExists;
    String seed;

    /**
     * Constructs a new `Menu` object with the specified TERenderer.
     *
     * @param ter The TERenderer used for rendering the menu.
     */
    public Menu(TERenderer ter) {
        this.ter = ter;
        this.key = '+';     // + -> no valid input
        this.handleSeed = false;
        this.isLoading = false;
        this.seed = "";
        StdDraw.setPenColor(Color.WHITE);
        savedGameExists = doesSavedFileExists();
        if (savedGameExists) {
            displayMenuWithLoadOption();
        } else {
            displayMenuWithoutLoadOption();
        }
    }

    /**
     * Runs the menu, capturing user input and displaying options until the user chooses to start a new game,
     * load a game, or quit the application.
     *
     * @return The seed entered by the user for starting a new game or loading an existing one.
     */
    public long run() {
        while (java.lang.Character.toLowerCase(key) != 's' || seed.length() < 1) {
            if (shouldRenderNewFrame()) {
                handleInput();
            }
        }
        System.out.print("HERE");
        loadingScreen();
        if (isLoading) {
            isLoading = false;
            return -1;
        }
        handleSeed = true;
        return Long.parseLong(seed);
    }

    private boolean doesSavedFileExists() {
        return FileUtils.fileExists("save-file.txt");
    }

    /**
     * Displays the initial menu with options for starting a new game, loading a game, or quitting.
     */
    public void displayMenuWithLoadOption() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 60));
        StdDraw.text(60 / 2.0, 40 / 1.5, "CS61B: THE GAME");
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        StdDraw.text(30, 16, "New Game (N)");
        StdDraw.text(30, 14, "Load Game (L)");
        StdDraw.text(30, 12, "Quit (Q)");
        StdDraw.show();
        StdDraw.setFont();
    }

    public void displayMenuWithoutLoadOption() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 60));
        StdDraw.text(60 / 2.0, 40 / 1.5, "CS61B: THE GAME");
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        StdDraw.text(30, 16, "New Game (N)");
        StdDraw.text(30, 14, "Quit (Q)");
        StdDraw.show();
        StdDraw.setFont();
    }

    /**
     * Displays the seed input menu, allowing the user to enter a seed for starting a new game.
     */
    private void seedMenu() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        StdDraw.text(30, 25, "Enter A Seed:");
        StdDraw.text(30, 20, seed);
        StdDraw.text(30, 15, "Press S to Start");
        StdDraw.setFont();
        StdDraw.show();
    }

    /**
     * Handles user input based on the current state of the menu (new game, load game, or seed input).
     */
    public void loadingScreen() {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        StdDraw.text(30, 30, "You are aboard an Alien Spacecraft");
        StdDraw.text(30, 25, "Unfortunatly you got capture by the Aliens.");
        StdDraw.text(30, 20, "Next time!, chose smaller key to run away from the Aliens");
        StdDraw.text(30, 5, "Good luck Soldier!");


        StdDraw.setFont();
        StdDraw.show();
        StdDraw.pause(50);

    }


    private void handleInput() {
        if (StdDraw.hasNextKeyTyped()) {
            this.key = StdDraw.nextKeyTyped();
            if (handleSeed) {
                if (key == 'S' || key == 's') {
                    System.out.println("Go to Game");
                    this.key = 's';
                } else if (java.lang.Character.isDigit(key)) {
                    seed += key;
                    System.out.println("Seed is: " + seed);
                    seedMenu();
                } else if (key == '\b') {
                    System.out.println("DELETE");
                    seed = seed.substring(0, Math.max(seed.length() - 1, 0));
                    seedMenu();
                }
            } else {
                switch (key) {
                    case 'N':
                    case 'n':
                        System.out.println("New Seed is: " + this.seed);
                        System.out.println("Go to SeedMenu");
                        handleSeed = true;
                        seedMenu();
                        break;
                    case 'L':
                    case 'l':
                        if (savedGameExists) {
                            this.isLoading = true;
                            System.out.println("Go to Load the game");
                            seed += "0";
                            this.key = 's';
                        }
                        break;
                    case 'Q':
                    case 'q':
                        System.out.println("Quit the game");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Key is not supported: " + key);
                        key = '+';
                }
            }
        }
    }

    /**
     * Checks whether a new frame should be rendered based on the elapsed time.
     *
     * @return True if a new frame should be rendered, false otherwise.
     */
    private boolean shouldRenderNewFrame() {
        if (frameDeltaTime() > 16) {
            resetFrameTimer();
            return true;
        }
        return false;
    }

    /**
     * Calculates the time elapsed since the previous frame was rendered.
     *
     * @return The time elapsed in milliseconds.
     */
    private long frameDeltaTime() {
        return System.currentTimeMillis() - prevFrameTimestamp;
    }

    /**
     * Resets the timer for tracking the elapsed time between frames.
     */
    private void resetFrameTimer() {
        prevFrameTimestamp = System.currentTimeMillis();
    }
}
