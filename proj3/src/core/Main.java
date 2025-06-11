package core;

import tileengine.TERenderer;
import tileengine.Tileset;


/**
 * The Main class is the entry point for the application. It initializes
 * the game board, generates rooms and hallways, and renders the result.
 *
 * @author Kyaw Min Khant
 * @since Apr 15 , 2025
 */
public class  Main {
    public static void main(String[] args) {
        // Theme selection
        final Theme necromancerTheme1 = new Theme(Tileset.WALL, Tileset.FLOOR,
                Tileset.NOTHING, Tileset.NECROMANCER, Tileset.NECROMANCER_STOP);

        final Theme necromancerTheme2 = new Theme(Tileset.WALL_ORANGE, Tileset.FLOOR_ORANGE,
                Tileset.NOTHING, Tileset.NECROMANCER, Tileset.NECROMANCER_STOP);

        final Theme necromancerTheme3 = new Theme(Tileset.WALL_YELLOW, Tileset.FLOOR_YELLOW,
                Tileset.NOTHING, Tileset.NECROMANCER, Tileset.NECROMANCER_STOP);

        final Theme necromancerTheme4 = new Theme(Tileset.WALL_GREEN, Tileset.FLOOR_GREEN,
                Tileset.NOTHING, Tileset.NECROMANCER, Tileset.NECROMANCER_STOP);

        final Theme necromancerTheme5 = new Theme(Tileset.WALL_BLUE, Tileset.FLOOR_BLUE,
                Tileset.NOTHING, Tileset.NECROMANCER, Tileset.NECROMANCER_STOP);

        final Theme necromancerTheme6 = new Theme(Tileset.WALL_PINK, Tileset.FLOOR_PINK,
            Tileset.NOTHING, Tileset.AVATAR, Tileset.AVATAR);

        Theme[] themes = {necromancerTheme1, necromancerTheme2, necromancerTheme3,
                             necromancerTheme4, necromancerTheme5, necromancerTheme6};
        // Rendering
        TERenderer ter = new TERenderer();
        ter.initialize(RoomPlot.BOARD_LENGTH, RoomPlot.BOARD_HEIGHT + 3); // initialize StdDraw

        // Game engine
        GameEngine engine = new GameEngine(ter, themes);
        engine.initialize();
    }
}
