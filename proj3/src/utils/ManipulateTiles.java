package utils;

import core.Character;
import core.Theme;
import tileengine.TETile;

/**
 * The {@code ManipulateTiles} class provides utility methods for manipulating
 * a 2D array of {@link TETile} tiles, with a focus on dark mode features.
 * It includes a method to generate a dark mode representation of the tiles
 * around a specified character location.
 *
 * @author Wai Han, Pranay Mallik
 * @version 1.2
 * @since Nov 17, 2023
 */
public class ManipulateTiles {
    public static TETile[][] darkMode(TETile[][] tiles, Character character, Theme theme, int halfLightBoxSize) {
        TETile[][] darkTiles = new TETile[tiles.length][tiles[0].length];
        for (int i = 0; i < darkTiles.length; i++) {
            for (int j = 0; j < darkTiles[0].length; j++) {
                darkTiles[i][j] = theme.getBackground();
            }
        }

        int x = character.getLocation().x();
        int y = character.getLocation().y();

        for (int i = x - halfLightBoxSize; i <= x + halfLightBoxSize; i++) {
            for (int j = y - halfLightBoxSize; j <= y + halfLightBoxSize; j++) {
                // Check bounds
                if (i >= 0 && i < darkTiles.length && j >= 0 && j < darkTiles[i].length) {
                    darkTiles[i][j] = tiles[i][j];
                }
            }
        }
        return darkTiles;
    }
}
