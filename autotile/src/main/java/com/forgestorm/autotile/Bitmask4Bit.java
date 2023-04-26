package com.forgestorm.autotile;

import java.util.List;

/**
 * The Bitmask4Bit class provides an implementation of tile bitmasking using the 4-bit variant. This class is designed
 * to be used in conjunction with an {@link AutoTiler} object and a {@link TileGetterSetter} object to automatically
 * select and place tiles in a 2D or 3D game environment.
 * <p>
 * The `initAutoTile` method initializes an auto-tile at the specified coordinates, using the given stripped tile name
 * and game world location. This method calculates the bitmask for the auto-tile using the `calculateBitmask` method,
 * sets the tile at the specified location, and updates the surrounding tiles as necessary.
 * <p>
 * The `calculateBitmask` method calculates the bitmask for the auto-tile at the specified coordinates, using the given
 * stripped tile name and optional flag to populate a list of neighboring auto-tiles. This method performs a directional
 * check on the surrounding tiles and calculates the bitmask value using the 4-bit bitmasking calculation.
 *
 * @author unenergizer
 * @see TileBitmasking
 * @see AutoTiler
 * @see TileGetterSetter
 */
class Bitmask4Bit implements TileBitmasking {
    /**
     * The {@link AutoTiler} object used by the bitmasking implementation.
     */
    private final AutoTiler autoTiler;

    /**
     * Constructs a new {@link Bitmask4Bit} object with the specified {@link AutoTiler}
     * and {@link TileGetterSetter} objects.
     *
     * @param autoTiler the {@link AutoTiler} object to be used by the bitmasking implementation
     */
    public Bitmask4Bit(AutoTiler autoTiler) {
        this.autoTiler = autoTiler;
    }

    @Override
    public int calculateBitmask(String strippedTileName, int x, int y, boolean populateList, List<TileLocations> locationUpdateList) {
        // Directional check
        boolean northTile = autoTiler.compareTile(strippedTileName, TileLocations.NORTH.getX(x), TileLocations.NORTH.getY(y));
        boolean westTile = autoTiler.compareTile(strippedTileName, TileLocations.WEST.getX(x), TileLocations.WEST.getY(y));
        boolean eastTile = autoTiler.compareTile(strippedTileName, TileLocations.EAST.getX(x), TileLocations.EAST.getY(y));
        boolean southTile = autoTiler.compareTile(strippedTileName, TileLocations.SOUTH.getX(x), TileLocations.SOUTH.getY(y));

        // If true, these tile locations will need to be updated
        if (northTile && populateList) locationUpdateList.add(TileLocations.NORTH);
        if (westTile && populateList) locationUpdateList.add(TileLocations.WEST);
        if (eastTile && populateList) locationUpdateList.add(TileLocations.EAST);
        if (southTile && populateList) locationUpdateList.add(TileLocations.SOUTH);

        // Perform 4 bit Bitmasking calculation
        return autoTiler.boolToInt(northTile) // 1 * northTile
                + 2 * autoTiler.boolToInt(westTile)
                + 4 * autoTiler.boolToInt(eastTile)
                + 8 * autoTiler.boolToInt(southTile);
    }
}
