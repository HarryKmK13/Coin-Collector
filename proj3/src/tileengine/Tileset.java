package tileengine;

import java.awt.Color;

/**
 * Contains constant tile objects, to avoid having to remake the same tiles in different parts of
 * the code.
 *
 * You are free to (and encouraged to) create and add your own tiles to this file. This file will
 * be turned in with the rest of your code.
 *
 * Ex:
 *      world[x][y] = Tileset.FLOOR;
 *
 * The style checker may crash when you try to style check this file due to use of unicode
 * characters. This is OK.
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you");
    //    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray, "wall");
//    public static final TETile BLUE_WALL = new TETile('#', new Color(54, 65, 220), Color.blue,
//            "blue wall");
//    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black,
    //  "floor");
    public static final TETile NECROMANCER = new TETile('@', Color.blue, Color.blue, "man", "NecromancerRun.png");
    public static final TETile NECROMANCER_STOP = new TETile('@', Color.blue, Color.blue, "man", "NecromancerStop.png");
    public static final TETile COIN_ICON = new TETile('.', Color.blue, Color.blue, "coin", "Coin.png");
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing");
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass");
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water");
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower");
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door");
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door");
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand");
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain");
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree");


    public static final TETile COIN = new TETile('♾', Color.YELLOW, Color.BLACK, "coin");
    /**
     * Theme 1: Space Theme
     * Walls are purple galaxy walls, floors are basic blue color themed, and the bakcground filled with stars, moon,sun
     * Avatar: TBD
     */

    public static final TETile SPACE_WALL = new TETile('#', Color.BLUE, new Color(102, 0, 204), "space_wall");
    public static final TETile WALL_ORANGE = new TETile('#', Color.RED, new Color(255, 95, 31),
            "space_wall");
    public static final TETile FLOOR_ORANGE = new TETile('·', Color.ORANGE, Color.black,
            "space_floor");

    public static final TETile WALL_YELLOW = new TETile('#', Color.GREEN, new Color(224, 231, 34),
            "space_wall");
    public static final TETile FLOOR_YELLOW = new TETile('·', Color.YELLOW, Color.black,
            "space_floor");

    public static final TETile WALL_GREEN = new TETile('#', Color.BLUE, new Color(57, 255, 20),
            "space_wall");
    public static final TETile FLOOR_GREEN = new TETile('·', Color.GREEN, Color.black,
            "space_floor");

    public static final TETile WALL_BLUE = new TETile('#', Color.BLACK, new Color(31, 81, 255),
            "space_wall");
    public static final TETile FLOOR_BLUE = new TETile('·', Color.BLUE, Color.black,
            "space_floor");

    public static final TETile WALL = new TETile('#', Color.BLUE, new Color(102, 0, 204),
            "space_wall");
    public static final TETile FLOOR = new TETile('·', Color.BLUE, Color.black,
            "space_floor");

    public static final TETile WALL_PINK = new TETile('#', Color.RED, new Color(255, 0, 255),
            "space_wall");
    public static final TETile FLOOR_PINK = new TETile('·', Color.PINK, Color.black,
            "space_floor");

    public static final TETile STAR = new TETile('★', Color.WHITE, Color.BLACK, "star");
    public static final TETile SUN = new TETile('☼', Color.YELLOW, Color.black, "sun");

    /***
     * Theme 3: Beach
     * Avatar: TBD
     */
    public static final TETile BEACH_WALL = new TETile('#', new Color(194, 178, 128), Color.darkGray,
            "wall");
    public static final TETile BEACH_FLOOR = new TETile('·', Color.BLACK, new Color(194, 178, 128),
            "floor");
    public static final TETile BEACH_NOTHING = new TETile(' ', new Color(35, 137, 218),
            new Color(35, 137, 218), "water");
}
