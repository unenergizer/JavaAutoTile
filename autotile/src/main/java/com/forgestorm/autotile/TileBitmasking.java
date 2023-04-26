package com.forgestorm.autotile;

import java.util.List;

/**
 * The {@link TileBitmasking) interface provides methods for initializing and calculating bitmasks for auto tiles
 * in a game world. Auto tiles are created by analyzing the surrounding tiles and automatically selecting
 * the appropriate tile to use based on predetermined rules and parameters. The bitmask for an auto tile
 * represents the type of tile and its orientation in the game world, and is used to determine which sprite
 * to display for the tile.
 *
 * @author unenergizer
 */
interface TileBitmasking {
    /**
     * Calculates the bitmask for the auto tile at the specified coordinates, using the given stripped tile name
     * and optional flag to populate a list of neighboring auto tiles.
     *
     * @param strippedTileName   the name of the stripped tile to use for the auto tile
     * @param x                  the x-coordinate of the auto tile in the game world
     * @param y                  the y-coordinate of the auto tile in the game world
     * @param populateList       true if a list of neighboring auto tiles should be populated; false otherwise
     * @param locationUpdateList The list of {@link TileLocations) to be updated after setting the initial auto-tile.
     * @return the calculated bitmask for the auto tile
     */
    int calculateBitmask(final String strippedTileName, final int x, final int y, boolean populateList, List<TileLocations> locationUpdateList);
}
