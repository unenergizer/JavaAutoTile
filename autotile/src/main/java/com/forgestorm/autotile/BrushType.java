package com.forgestorm.autotile;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrushType {
    SINGLE(1,
            new BrushTileInfo[]{
                    new BrushTileInfo(0, 0, 0) // Single
            }
    ),
    DOUBLE(4,
            new BrushTileInfo[]{
                    // Column 1
                    new BrushTileInfo(0, 1, 22), // Top Left
                    new BrushTileInfo(0, 0, 208), // Bottom Left

                    // Column 2
                    new BrushTileInfo(1, 1, 11), // Top Right
                    new BrushTileInfo(1, 0, 104) // Bottom Right
            }
    ),
    TRIPLE(6,
            new BrushTileInfo[]{
                    // Column 1
                    new BrushTileInfo(0, 2, 22), // Left Top
                    new BrushTileInfo(0, 1, 214), // Left Middle
                    new BrushTileInfo(0, 0, 208), // Left Bottom

                    // Column 2
                    new BrushTileInfo(1, 2, 31), // Middle Top
                    new BrushTileInfo(1, 1, 255), // Center
                    new BrushTileInfo(1, 0, 248), // Middle Bottom

                    // Column 3
                    new BrushTileInfo(2, 2, 11), // Right Top
                    new BrushTileInfo(2, 1, 107), // Right Middle
                    new BrushTileInfo(2, 0, 104) // Right Bottom
            }
    );

    /**
     * The number of tiles to be placed for this brush size.
     */
    private final int tileCount;
    /**
     * Contains information needed to place tiles for the specified brush size.
     */
    private final BrushTileInfo[] brushTileInfo;

    /**
     * This contains information about the tile and location of a brush.
     */
    @Getter
    @AllArgsConstructor
    static class BrushTileInfo {
        /**
         * The value that needs to be added to the X & Y axis in order to place this tile.
         */
        private int addX, addY;
        /**
         * The ID of the tile that needs to be placed for this brush.
         */
        private int tileId;
    }
}
