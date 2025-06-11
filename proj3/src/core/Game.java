package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.Audio;
import utils.ManipulateTiles;
import utils.SnapshotUtils;

import java.awt.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The `Game` class represents the main game loop, allowing the player to control a character
 * in a dynamically changing world. It handles the rendering of tiles, user input, and the game loop itself.
 *
 * The game supports basic movement controls (left, right, up, down) and allows the player to save the game
 * state and exit with a specific key combination.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 2025
 */
public class Game {
    private static final double DEFAULT_TIMER = 60 ;
    private TERenderer ter;
    private World w;
    private TETile[][] tiles;
    private Character character;
    private long seed;
    private long prevFrameTimestamp;
    private long prevTimerTime;
    private char prevKey = ' ';
    private Theme theme;
    private boolean darkMode;
    private final int FRAME_RATE = 20;
    private int scoreToWin;
    private double countDown;
    private ScheduledExecutorService scheduler;
    private CountDownLatch timerLatch;
    private long startTime;
    private double remainingSeconds;
    private boolean isLost;
    private boolean isWin;
    private int lightBoxSize;
    int mouseX = 0;
    int mouseY = 0;



    /**
     * Constructs a new `Game` object with the specified seed, TERenderer, and theme.
     *
     * @param seed      The seed for generating the game world.
     * @param ter       The TERenderer for rendering the game world.
     * @param countDown The countdown duration for the game.
     * @param theme     The theme used for character and floor tiles.
     */
    public Game(long seed, TERenderer ter, double countDown, Theme theme) {
        this.darkMode = false;
        this.seed = seed;
        this.ter = ter;
        this.theme = theme;
        this.isLost = false;
        this.isWin = false;
        this.lightBoxSize = 5;
        this.countDown = countDown;
    }

    /**
     * Initializes the game by creating a new world and obtaining the initial state.
     */
    public void initialize() {
        this.w = new World(seed, theme);
        this.tiles = w.getTiles();
        this.character = w.getCharacter();
        this.scoreToWin = w.getTotalNumCoins();
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.timerLatch = new CountDownLatch(1);

        // Schedule the timer task
        // @Source - ChatGpt
        scheduler.schedule(() -> {
            System.out.println("Timer run out");
            this.isLost = true;
            timerLatch.countDown();
        }, (long) countDown, TimeUnit.SECONDS);

        this.startTime = System.currentTimeMillis();
    }

    /**
     * Modifies the character's location on the world tiles and updates the display.
     *
     * @param x The new x-coordinate of the character.
     * @param y The new y-coordinate of the character.
     */
    public void modifyCharacterLocation(int x, int y) {
        w.getTiles()[character.getLocation().x()][character.getLocation().y()] = theme.getFloor();
        w.getTiles()[x][y] = theme.getCharacter();
        this.character.setLocation(x, y);
    }


    public Character getCharacter() {
        return this.character;
    }


    public void hideCollectibleTile(int x, int y) {
        w.getTiles()[x][y] = theme.getFloor();
    }


    /**
     * Runs the main game loop, continuously rendering tiles and updating the game state based on user input.
     */
    public void run() {
        renderTiles();
        while (!isLost && !isWin) {
            long currentTime = System.currentTimeMillis();
            long elapsedSeconds = (currentTime - startTime) / 1000;
            this.remainingSeconds = countDown - (int) elapsedSeconds;

            // fun mode
            // if(this.remainingSeconds % 5 != 0) {
            //      this.darkMode = true;
            // } else {
            // this.darkMode = false;
            // }


            if (character.getMyScore() >= this.scoreToWin) {
                System.out.println("You Won the Game!");
                isWin = true;
            }


            if (shouldRenderNewFrame()) {
                updateTiles();
            }

            if (shouldRenderAfterOneSecond()) {
                if (this.remainingSeconds <= 6) {
                    this.darkMode = true;
                    if (this.lightBoxSize >= 2) {
                        this.lightBoxSize--;
                    }
                    Audio.playAudio("Countdown.wav");
                }
                renderTiles();
            }
        }

        // Handle game over and win conditions
        if (isLost) {
            renderTiles();
            System.out.println("You lost the game");
            Audio.playAudio("Gameover.wav");
            renderGameOver();
            waitForRestartOrQuit();
        } else if (isWin) {
            System.out.println("You won the game");
            Audio.playAudio("winGame.wav");
            renderWinGame();
            waitForRestartOrQuit();
        }
        this.scheduler.shutdown();
    }

    private void waitForRestartOrQuit() {
        while (true) {
            if(StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (key == 'Q' || key == 'q') {
                    System.exit(0);
                } else if (key == 'R' || key == 'r') {
                    restartGame();
                    break;
                }
            }
        }
    }

    private void restartGame() {
        this.scheduler.shutdown();

        Game newGame = new Game(seed, ter, DEFAULT_TIMER, theme);
        newGame.initialize();
        newGame.run();
    }


    /**
     * Updates the game state based on user input.
     */
    private void updateTiles() {
        if (StdDraw.mouseX() < 60 && StdDraw.mouseX() != mouseX
                && StdDraw.mouseY() < 40 && StdDraw.mouseY() != mouseY) {
            renderLocationText();
            mouseY = (int) StdDraw.mouseY();
            mouseX = (int) StdDraw.mouseX();
        }

        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            key = java.lang.Character.toLowerCase(key);
            if (key == ':') {
                prevKey = ':';
            } else if (key == 'b') {
                prevKey = ' ';
                this.darkMode = !this.darkMode;
            } else if (key == 'a') {
                prevKey = ' ';
                character.move(-1, 0);
            } else if (key == 's') {
                prevKey = ' ';
                character.move(0, -1);
            } else if (key == 'd') {
                prevKey = ' ';
                character.move(1, 0);
            } else if (key == 'w') {
                prevKey = ' ';
                character.move(0, 1);
            } else if (key == 'q' && prevKey == ':') {
                prevKey = ' ';
                // Save Snapshot and Exit Here
                System.out.println("Quit Game");
                SnapshotUtils.saveSeed(this.seed, this.character, this.remainingSeconds);
                System.exit(0);
            } else {
                prevKey = ' ';
                System.out.println("Does not support the key: " + key);
            }
            renderTiles();
        }
    }


    private void renderGameOver() {
        while (true) {
            if (shouldRenderNewFrame()) {
                StdDraw.clear(Color.BLACK);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
                StdDraw.text(30, 30, "Game Over!");
                StdDraw.text(30, 25, "Good luck Next Time!");
                StdDraw.text(30, 20, "Press 'Q' to Quit the Game");
                StdDraw.text(30,17,"Press 'R' to restart the Game");
                StdDraw.setFont();
                StdDraw.show();

                if (StdDraw.hasNextKeyTyped()) {
                    char key = java.lang.Character.toLowerCase(StdDraw.nextKeyTyped());
                    if (key == 'q') {
                        System.out.println("Quit the Game");
                        System.exit(0);
                    } else if (key == 'r') {
                        restartGame();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Renders the win game screen.
     * This method displays a congratulatory message and instructions when the player wins the game.
     * It continuously renders the win screen until the player decides to quit by pressing 'Q'.
     */
    private void renderWinGame() {
        while (true) {
            if (shouldRenderNewFrame()) {
                StdDraw.clear(Color.BLACK);
                StdDraw.setPenColor(Color.WHITE);
                StdDraw.setFont(new Font("Comic Sans MS", Font.PLAIN, 35));
                StdDraw.text(30, 30, "Congratulations! You won the Game");
                StdDraw.text(30, 25, "Thanks for playing the Game");
                StdDraw.text(30, 20, "Press 'Q' to Quit the Game");
                StdDraw.text(30,17,"Press 'R' to restart the Game");

                StdDraw.setFont();
                StdDraw.show();

                if (StdDraw.hasNextKeyTyped()) {
                    char key = java.lang.Character.toLowerCase(StdDraw.nextKeyTyped());
                    if (key == 'q') {
                        System.out.println("Quit the Game");
                        System.exit(0);
                    } else if (key == 'r') {
                        restartGame();
                        break;
                    }
                }
            }
        }
    }

    /**
     * Renders the current state of the tiles on the screen, including the character's location.
     */
    private void renderTiles() {
        if (this.darkMode) {
            TETile[][] darkTiles = ManipulateTiles.darkMode(tiles, character, theme, this.lightBoxSize);
            ter.renderFrame(darkTiles);
        } else {
            ter.renderFrame(this.tiles);
        }
        renderTips();
        renderLocationText();
        renderSecondsLeft();
    }

    /**
     * Renders the remaining time on the screen.
     * Displays the remaining time left in seconds for the player to complete the game.
     */
    private void renderSecondsLeft() {
        String text = "Time Left: " + this.remainingSeconds;
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(55, RoomPlot.BOARD_HEIGHT + 2, text);
        StdDraw.show();
    }


    /**
     * Renders helpful tips and information on the screen.
     * Display tips for player actions and relevant information, including the keys for losing vision,
     * saving and quitting the game, and the number of remaining coins.
     */
    private void renderTips() {
        String text = "[B] to lose vision | [:q] to save and quit";
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(22, RoomPlot.BOARD_HEIGHT + 2, text);

        int coinsLeft = w.getTotalNumCoins() - character.getMyScore();
        String text2 = "Coins Left: " + coinsLeft + "|";
        StdDraw.text(47, RoomPlot.BOARD_HEIGHT + 2, text2);

        StdDraw.show();
    }

    /**
     * Renders the current location of the character on the screen.
     */
    private void renderLocationText() {

        int xPressed = (int) StdDraw.mouseX();
        int yPressed = (int) StdDraw.mouseY();
        if (xPressed > 59 || yPressed > 39) {
            return;
        }
        String text = "";
        StdDraw.setPenColor(Color.BLACK);
        StdDraw.filledRectangle(6, RoomPlot.BOARD_HEIGHT + 2, 5, 1);
        StdDraw.setPenColor(Color.WHITE);
        if (tiles[xPressed][yPressed] == theme.getWall()) {
            text = "Object: Wall";
        }

        if (tiles[xPressed][yPressed] == theme.getBackground()) {
            text = "Object: Nothing";
        }

        if (tiles[xPressed][yPressed] == theme.getFloor()) {
            text = "Object: Floor";
        }

        if (tiles[xPressed][yPressed] == Tileset.COIN_ICON) {
            text = "Object: Coin";
        }

        if (tiles[xPressed][yPressed] == theme.getCharacter2() || tiles[xPressed][yPressed] == theme.getCharacter()) {
            text = "Object: Character";
        }

        //String text = "Item: " + xPressed + " " + yPressed;
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(6, RoomPlot.BOARD_HEIGHT + 2, text);
        StdDraw.show();
    }

    /**
     * Checks whether a new frame should be rendered based on the elapsed time.
     *
     * @return True if a new frame should be rendered, false otherwise.
     */
    private boolean shouldRenderNewFrame() {
        if (frameDeltaTime() > FRAME_RATE) {
            resetFrameTimer();
            return true;
        }
        return false;
    }

    /**
     * Checks whether a new frame should be rendered after one second based on the elapsed time.
     *
     * @return True if a new frame should be rendered after one second, false otherwise.
     */
    private boolean shouldRenderAfterOneSecond() {
        if (timerDeltaTime() > 1000) {
            resetTimer();
            return true;
        }
        return false;
    }

    /**
     * Calculates the time elapsed since the previous timer reset.
     *
     * @return The time elapsed in milliseconds.
     */
    private long timerDeltaTime() {
        return System.currentTimeMillis() - prevTimerTime;
    }

    /**
     * Resets the timer for tracking the elapsed time between frames.
     */
    private void resetTimer() {
        prevTimerTime = System.currentTimeMillis();
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

