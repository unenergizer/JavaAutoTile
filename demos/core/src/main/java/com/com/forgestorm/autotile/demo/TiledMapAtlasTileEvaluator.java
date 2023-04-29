package com.forgestorm.autotile.demo;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.forgestorm.autotile.TileGetterSetter;

public class TiledMapAtlasTileEvaluator implements TileGetterSetter {

    private final MapHandler mapHandler;
    private final TiledMap tiledMap;
    private final TextureAtlas textureAtlas;

    public TiledMapAtlasTileEvaluator(MapHandler mapHandler) {
        this.mapHandler = mapHandler;
        this.tiledMap = mapHandler.getTiledMap();
        this.textureAtlas = mapHandler.getTextureAtlas();
    }

    @Override
    public String getTile(int x, int y) {
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(mapHandler.getActiveLayer());
        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(x, y);

        if (cell == null) return null;

        TiledMapTile tiledMapTile = cell.getTile();

        if (tiledMapTile == null) return null;

        // Try to find the region in the texture atlas
        return findRegionName(tiledMapTile.getTextureRegion());
    }

    @Override
    public void setTile(String tileName, int x, int y) {
        TiledMapTileLayer tiledMapTileLayer = (TiledMapTileLayer) tiledMap.getLayers().get(mapHandler.getActiveLayer());
        TiledMapTileLayer.Cell cell = tiledMapTileLayer.getCell(x, y);

        // Create a new cell if one does not exist.
        if (cell == null) {
            tiledMapTileLayer.setCell(x, y, new TiledMapTileLayer.Cell());
        }

        TiledMapTile tiledMapTile = cell.getTile();
        TextureRegion textureRegion = textureAtlas.findRegion(tileName);

        // Make sure the texture we are trying to get exists!
        if (textureRegion == null) return;

        if (tiledMapTile == null) {
            cell.setTile(new StaticTiledMapTile(textureRegion));
        } else {
            tiledMapTile.setTextureRegion(textureRegion);
        }
    }

    private String findRegionName(TextureRegion textureRegion) {
        String foundTileName = null;
        for (TextureAtlas.AtlasRegion atlasRegion : textureAtlas.getRegions()) {
            if (atlasRegion.equals(textureRegion)) {
                foundTileName = atlasRegion.name;
                break;
            }
        }
        return foundTileName;
    }
}
