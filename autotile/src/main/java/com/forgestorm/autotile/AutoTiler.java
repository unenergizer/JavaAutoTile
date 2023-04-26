package com.forgestorm.autotile;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * An auto-tiler is a tool used in video game development to automate
 * the process of selecting and placing tiles in a game world. A tile
 * is a small, rectangular graphic used to create the visual elements
 * of a game environment, such as the ground, walls, and other
 * structures.
 * <p>
 * An auto-tiler analyzes the game environment and automatically
 * selects the appropriate tile to use based on predetermined rules
 * and parameters. This can help game developers save time and effort
 * in creating the game world by automating a repetitive task.
 * <p>
 * Auto-tilers typically work by analyzing the surrounding tiles and
 * determining which tile would be the best fit based on factors such
 * as the shape, texture, and orientation of the neighboring tiles.
 * Some auto-tilers also allow developers to set specific rules and
 * preferences, such as ensuring that certain tiles are not placed
 * too close to each other or prioritizing the use of certain tiles
 * in certain areas of the game world.
 *
 * @author unenergizer
 */
public class AutoTiler {

    /**
     * Tile getter and setter implementation supplied by the library user.
     */
    private final TileGetterSetter tileGetterSetter;
    /**
     * The {@link Bitmask4Bit} class provides an implementation of
     * auto-tiling based on the 4-bit variant. This implementation
     * supports an auto-tiling range of 0-15 tiles.
     */
    private final Bitmask4Bit bitmask4Bit;
    /**
     * The {@link Bitmask8Bit} class provides an implementation of
     * auto-tiling based on the 8-bit variant. This implementation
     * supports an auto-tiling range of 0-47 tiles.
     */
    private final Bitmask8Bit bitmask8Bit;
    /**
     * The list of {@link TileLocations} to be updated after setting the initial auto-tile.
     */
    private final List<TileLocations> locationUpdateList = new LinkedList<>();
    /**
     * The list of {@link TileLocations} to be updated after editing an auto-tile.
     */
    private final List<TileLocations> fixNeighborList = new LinkedList<>();

    /**
     * If true, neighboring tiles will be fixed when setting a new auto-tile over
     * an existing one or when erasing an auto-tile.
     */
    @Getter
    @Setter
    private boolean fixNeighborTiles = true;

    public AutoTiler(TileGetterSetter tileGetterSetter) {
        this.tileGetterSetter = tileGetterSetter;
        this.bitmask4Bit = new Bitmask4Bit(this);
        this.bitmask8Bit = new Bitmask8Bit(this);
    }

    /**
     * Attempts to perform an auto tile on the supplied tile name.
     *
     * @param tileName The tile we want to auto tile.
     * @param x        The X location of the map.
     * @param y        The Y location of the map.
     * @return True if auto tiling worked, false otherwise.
     */
    public boolean autoTile(String tileName, int x, int y) {
        // Detect the type of bitmask tile we are working with.
        BitmaskingType bitmaskingType = BitmaskingType.detectBitmaskingType(tileName);
        if (bitmaskingType == null) return false; // Type not detected

        // Strip the image id from the tile name. Good for tile comparisons.
        String strippedTileName = stripImageId(tileName);

        // If enabled, prepare neighbor tiles fix.
        prepareNeighborTilesFix(strippedTileName, x, y);

        // Perform bitmasking auto tile operations based on tile type
        if (bitmaskingType == BitmaskingType.TYPE_4) {
            initAutoTile(bitmask4Bit, strippedTileName, x, y);
        } else if (bitmaskingType == BitmaskingType.TYPE_8) {
            initAutoTile(bitmask8Bit, strippedTileName, x, y);
        }

        // If enabled, do neighbor tiles fix.
        initNeighborTileFix(x, y);

        return true;
    }

    /**
     * Erases the tile at the specified coordinates and updates its neighbors.
     *
     * @param x the x-coordinate of the tile in the game world
     * @param y the y-coordinate of the tile in the game world
     */
    public void eraseTile(int x, int y) {
        if (!fixNeighborTiles) return;
        prepareNeighborTilesFix("", x, y);
        initNeighborTileFix(x, y);
    }

    /**
     * Prepares a list of neighboring tiles to be fixed after the auto-tiling operation.
     * This method is only used when the fixNeighborTiles option is enabled.
     *
     * @param strippedTileName the name of the stripped tile to compare with neighboring tiles
     * @param x                the x-coordinate of the tile in the game world
     * @param y                the y-coordinate of the tile in the game world
     */
    private void prepareNeighborTilesFix(String strippedTileName, int x, int y) {
        if (!fixNeighborTiles) return;

        // See if we are currently replacing a blob tile
        String neighborName = tileGetterSetter.getTile(x, y);

        // If the neighbor
        String strippedNeighborName = null;
        if (neighborName != null) {
            strippedNeighborName = stripImageId(neighborName);

            // If the stripped tile names are the same, skip
            if (strippedNeighborName.equals(strippedTileName)) return;
        }

        // Now loop through and find tiles that need to be fixed
        for (TileLocations tileLocation : TileLocations.values()) {
            int locationX = tileLocation.getX(x);
            int locationY = tileLocation.getY(y);
            String neighborsNeighborName = tileGetterSetter.getTile(locationX, locationY);
            if (neighborsNeighborName == null) continue;
            if (strippedNeighborName != null && !(neighborsNeighborName.contains(strippedNeighborName))) continue;
            fixNeighborList.add(tileLocation);
        }
    }

    /**
     * Initializes an auto tile at the specified coordinates, using the given stripped tile name
     * and game world location.
     *
     * @param strippedTileName the name of the stripped tile to use for the auto tile
     * @param x                the x-coordinate of the auto tile in the game world
     * @param y                the y-coordinate of the auto tile in the game world
     */
    private void initAutoTile(TileBitmasking tileBitmasking, String strippedTileName, int x, int y) {
        // Set target location first
        int bitmask = tileBitmasking.calculateBitmask(strippedTileName, x, y, true, locationUpdateList);
        tileGetterSetter.setTile(strippedTileName + bitmask, x, y);

        // Update surrounding tiles
        for (TileLocations tileLocation : locationUpdateList) {
            int locationX = tileLocation.getX(x);
            int locationY = tileLocation.getY(y);

            // These are tiles that are of the same type
            bitmask = tileBitmasking.calculateBitmask(strippedTileName, locationX, locationY, false, locationUpdateList);
            String tileName = strippedTileName + bitmask;
            tileGetterSetter.setTile(tileName, locationX, locationY);
        }
        locationUpdateList.clear();
    }

    /**
     * Fixes the neighbor tiles affected by the auto-tiling or erase operation.
     * This method is only used when the fixNeighborTiles option is enabled.
     *
     * @param x the x-coordinate of the tile in the game world
     * @param y the y-coordinate of the tile in the game world
     */
    private void initNeighborTileFix(int x, int y) {
        if (!fixNeighborTiles) return;
        for (TileLocations blobLocation : fixNeighborList) {
            int locationX = blobLocation.getX(x);
            int locationY = blobLocation.getY(y);

            // These are surrounding tiles that are not the same type
            String neighborName = tileGetterSetter.getTile(locationX, locationY);
            if (neighborName == null) continue;

            String strippedNeighborName = stripImageId(neighborName);

            // Detect bitmasking type for this tile
            BitmaskingType bitmaskingType = BitmaskingType.detectBitmaskingType(strippedNeighborName);
            if (bitmaskingType == null) return; // Type not detected

            // Apply bitmasking operations
            int bitmask = 0;
            if (bitmaskingType == BitmaskingType.TYPE_4) {
                bitmask = bitmask4Bit.calculateBitmask(strippedNeighborName, locationX, locationY, false, locationUpdateList);
            } else if (bitmaskingType == BitmaskingType.TYPE_8) {
                bitmask = bitmask8Bit.calculateBitmask(strippedNeighborName, locationX, locationY, false, locationUpdateList);
            }
            tileGetterSetter.setTile(strippedNeighborName + bitmask, locationX, locationY);
        }
        fixNeighborList.clear();
    }

    /**
     * Compares the supplied tile name to the one at the X/Y location.
     *
     * @param strippedTileName A tile name with the image id removed.
     * @param x                The X location of the map.
     * @param y                The Y location of the map.
     * @return True if the names match, false if they do not match or are null.
     */
    boolean compareTile(String strippedTileName, int x, int y) {
        String targetTile = tileGetterSetter.getTile(x, y);

        if (targetTile == null) return false;

        targetTile = stripImageId(targetTile);
        return strippedTileName.equals(targetTile);
    }

    /**
     * Converts a boolean to a numerical value.
     *
     * @param b the boolean to test.
     * @return 1 if true or 0 if false.
     */
    int boolToInt(boolean b) {
        return b ? 1 : 0;
    }

    /**
     * Gets the texture name and strips out its wang image id.
     *
     * @param textureName The name of the texture.
     * @return A texture name with the numbers stripped out.
     */
    public String stripImageId(String textureName) {
        return textureName.replaceAll("\\d*$", "");
    }
}
