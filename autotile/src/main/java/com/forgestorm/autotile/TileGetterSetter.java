package com.forgestorm.autotile;

/**
 * The {@link TileGetterSetter} interface provides methods for getting and setting tiles in a map. Each tile represents a
 * visual element of the map, such as a ground texture or a wall. The `getTile` method returns the name of the tile
 * at a specified location on the map, while the `setTile` method sets the name of a tile at a specified location.
 * <p>
 * This interface should be implemented by programmers who wish to create an auto-tiling setup for their map. By
 * implementing this interface, programmers can define the exact behavior of the {@link AutoTiler} when interacting
 * with their map implementation.
 * <p>
 * Implementing this interface allows for flexibility and customization of the auto-tiling process, as the programmer
 * can specify how to retrieve and update tile information for their map. This interface provides a standardized way
 * to interact with the auto-tiling process and ensures compatibility between the map implementation and the auto-tiler.
 *
 * @author unenergizer
 */
public interface TileGetterSetter {

    /**
     * Gets a tile at a location.
     *
     * @param x The X location of a map.
     * @param y The Y (or in case of 3D a Z) location of a map.
     * @return The tile name of the tile at this location. Can return null.
     */
    String getTile(int x, int y);

    /**
     * Set a tile at a location.
     *
     * @param tileName The name of the tile to set.
     * @param x        The X location of a map.
     * @param y        The Y (or in case of 3D a Z) location of a map.
     */
    void setTile(String tileName, int x, int y);
}
