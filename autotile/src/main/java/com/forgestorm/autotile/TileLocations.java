package com.forgestorm.autotile;

import lombok.AllArgsConstructor;

/**
 * The {@link TileLocations} enum represents the relative positions of tiles
 * surrounding a center tile in a two-dimensional grid. Each location
 * is defined by its x- and y-coordinates relative to the center
 * tile's location. The positions are arranged in a 3x3 grid, with
 * the center position at (0,0) and the surrounding positions below.
 */
@AllArgsConstructor
enum TileLocations {
    NORTH_WEST(-1, -1),
    NORTH(0, -1),
    NORTH_EAST(1, -1),
    WEST(-1, 0),
    EAST(1, 0),
    SOUTH_WEST(-1, 1),
    SOUTH(0, 1),
    SOUTH_EAST(1, 1);

    /**
     * The x-coordinate of the tile's location relative to the
     * center tile's location.
     */
    private final int x;

    /**
     * The y-coordinate of the tile's location relative to the
     * center tile's location.
     */
    private final int y;

    public int getX(int tileX) {
        return x + tileX;
    }

    public int getY(int tileY) {
        return y + tileY;
    }
}
