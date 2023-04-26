package com.forgestorm.autotile;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The {@link BitmaskingType} enum represents different types of bitmasking used in wang tiling, a technique used in
 * computer graphics to create seamless textures. Each bitmasking type is associated with a prefix, a UI wang tile
 * ID, a default wang tile image ID, and a maximum number of tile images. The class provides methods for detecting
 * and stripping out the wang image ID from a texture name.
 */
@Getter
@AllArgsConstructor
enum BitmaskingType {
    TYPE_4("BW4=", "0"),
    TYPE_8("BW16=", "90");

    /**
     * The prefix of a wang tile image used at the beginning of a texture name.
     */
    private final String prefix;
    /**
     * The default tile image ID used at the end of a texture name. This ID is used to initiate drawing of a wang tile.
     */
    private final String defaultTileImageId;

    /**
     * Detects if the supplied texture name is a wang tile.
     *
     * @param textureName The name of the texture to check.
     * @return Returns the {@link BitmaskingType} if it is detected.
     */
    public static BitmaskingType detectBitmaskingType(String textureName) {
        for (BitmaskingType BitmaskingType : values()) {
            if (textureName.startsWith(BitmaskingType.getPrefix())) return BitmaskingType;
        }
        return null;
    }
}
