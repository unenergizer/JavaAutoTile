package com.forgestorm.autotile;

import java.util.List;

/**
 * The {@link Bitmask8Bit} class provides an implementation of tile bitmasking using the 8-bit variant. This class is designed
 * to be used in conjunction with an {@link AutoTiler} object and a {@link TileGetterSetter} object to automatically
 * select and place tiles in a 2D or 3D game environment.
 * <p>
 * The `initAutoTile` method initializes an auto-tile at the specified coordinates, using the given stripped tile name
 * and game world location. This method calculates the bitmask for the auto-tile using the `calculateBitmask` method,
 * sets the tile at the specified location, and updates the surrounding tiles as necessary.
 * <p>
 * The `calculateBitmask` method calculates the bitmask for the auto-tile at the specified coordinates, using the given
 * stripped tile name and optional flag to populate a list of neighboring auto-tiles. This method performs a directional
 * check on the surrounding tiles and calculates the bitmask value using the 8-bit bitmasking calculation.
 *
 * @author unenergizer
 * @see TileBitmasking
 * @see AutoTiler
 * @see TileGetterSetter
 */
class Bitmask8Bit implements TileBitmasking {
    /**
     * The {@link AutoTiler} object used by the bitmasking implementation.
     */
    private final AutoTiler autoTiler;

    /**
     * Constructs a new {@link Bitmask8Bit} object with the specified {@link AutoTiler}
     * and {@link TileGetterSetter} objects.
     *
     * @param autoTiler the {@link AutoTiler} object to be used by the bitmasking implementation
     */
    public Bitmask8Bit(AutoTiler autoTiler) {
        this.autoTiler = autoTiler;
    }

    @Override
    public int calculateBitmask(String strippedTileName, int x, int y, boolean populateList, List<TileLocations> locationUpdateList) {
        // Directional Check, including corners, returns Boolean
        boolean northTile = autoTiler.compareTile(strippedTileName, TileLocations.NORTH.getX(x), TileLocations.NORTH.getY(y, autoTiler.isYUp()));
        boolean southTile = autoTiler.compareTile(strippedTileName, TileLocations.SOUTH.getX(x), TileLocations.SOUTH.getY(y, autoTiler.isYUp()));
        boolean westTile = autoTiler.compareTile(strippedTileName, TileLocations.WEST.getX(x), TileLocations.WEST.getY(y, autoTiler.isYUp()));
        boolean eastTile = autoTiler.compareTile(strippedTileName, TileLocations.EAST.getX(x), TileLocations.EAST.getY(y, autoTiler.isYUp()));
        boolean northWestTile = autoTiler.compareTile(strippedTileName, TileLocations.NORTH_WEST.getX(x), TileLocations.NORTH_WEST.getY(y, autoTiler.isYUp())) && westTile && northTile;
        boolean northEastTile = autoTiler.compareTile(strippedTileName, TileLocations.NORTH_EAST.getX(x), TileLocations.NORTH_EAST.getY(y, autoTiler.isYUp())) && northTile && eastTile;
        boolean southWestTile = autoTiler.compareTile(strippedTileName, TileLocations.SOUTH_WEST.getX(x), TileLocations.SOUTH_WEST.getY(y, autoTiler.isYUp())) && southTile && westTile;
        boolean southEastTile = autoTiler.compareTile(strippedTileName, TileLocations.SOUTH_EAST.getX(x), TileLocations.SOUTH_EAST.getY(y, autoTiler.isYUp())) && southTile && eastTile;

        // If true, these tile locations will need to be updated
        if (northTile && populateList) locationUpdateList.add(TileLocations.NORTH);
        if (southTile && populateList) locationUpdateList.add(TileLocations.SOUTH);
        if (westTile && populateList) locationUpdateList.add(TileLocations.WEST);
        if (eastTile && populateList) locationUpdateList.add(TileLocations.EAST);
        if (northWestTile && populateList) locationUpdateList.add(TileLocations.NORTH_WEST);
        if (northEastTile && populateList) locationUpdateList.add(TileLocations.NORTH_EAST);
        if (southWestTile && populateList) locationUpdateList.add(TileLocations.SOUTH_WEST);
        if (southEastTile && populateList) locationUpdateList.add(TileLocations.SOUTH_EAST);

        // 8 bit Bitmasking calculation using Directional check booleans values
        return autoTiler.boolToInt(northWestTile) // 1 * northWestTile
            + 2 * autoTiler.boolToInt(northTile)
            + 4 * autoTiler.boolToInt(northEastTile)
            + 8 * autoTiler.boolToInt(westTile)
            + 16 * autoTiler.boolToInt(eastTile)
            + 32 * autoTiler.boolToInt(southWestTile)
            + 64 * autoTiler.boolToInt(southTile)
            + 128 * autoTiler.boolToInt(southEastTile);
    }
}
