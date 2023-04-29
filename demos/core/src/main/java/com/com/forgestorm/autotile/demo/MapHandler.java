package com.forgestorm.autotile.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.forgestorm.autotile.AutoTiler;
import lombok.Getter;
import lombok.Setter;

public class MapHandler extends ApplicationAdapter {

    public static final int MAP_WIDTH = 16;
    public static final int MAP_HEIGHT = 12;
    public static final int TILE_SIZE = 16;
    private final AssetManager assetManager = new AssetManager();
    private final OrthographicCamera camera;
    @Getter
    private AutoTiler autoTiler;
    @Getter
    private TiledMap tiledMap;

    @Getter
    private TextureAtlas textureAtlas;
    @Setter
    private String tileName = "BW4=brick-wall-building-h32=0";
    @Getter
    @Setter
    private int activeLayer = 0;
    private OrthogonalTiledMapRenderer renderer;

    public MapHandler(OrthographicCamera camera) {
        this.camera = camera;
    }

    @Override
    public void create() {
        // The tile images are loaded inside an atlas
        assetManager.load("ground.atlas", TextureAtlas.class);
        assetManager.update();
        assetManager.finishLoading();
        textureAtlas = assetManager.get("ground.atlas", TextureAtlas.class);

        // Create the TiledMap
        tiledMap = new TiledMap();

        // Manually create a TiledMapTileLayer to hold the tiles
        TiledMapTileLayer layer0 = new TiledMapTileLayer(MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, TILE_SIZE);
        TiledMapTileLayer layer1 = new TiledMapTileLayer(MAP_WIDTH, MAP_HEIGHT, TILE_SIZE, TILE_SIZE);
        tiledMap.getLayers().add(layer0);
        tiledMap.getLayers().add(layer1);
        populateLayer(layer0);
        populateLayer(layer1);

        // Setup renderer
        renderer = new OrthogonalTiledMapRenderer(tiledMap);
        renderer.setView(camera);

        // Setup auto tiler
        autoTiler = new AutoTiler(new TiledMapAtlasTileEvaluator(this));
    }

    @Override
    public void render() {
        renderer.render();
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        textureAtlas.dispose();
        assetManager.dispose();
    }

    public void setTile(int tileX, int tileY, boolean eraseTile) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tiledMap.getLayers().get(activeLayer);
        TiledMapTileLayer.Cell cell = layer.getCell(tileX, tileY);

        if (cell == null) return;

        if (!eraseTile) {
            // Will attempt to do auto tiling here.
            boolean tileSet = autoTiler.autoTile(tileName, tileX, tileY);

            // Auto tiling didn't work. So we set the supplied tile texture instead.
            if (!tileSet) {
                TextureRegion textureRegion = textureAtlas.findRegion(tileName);
                if (textureRegion != null) cell.setTile(new StaticTiledMapTile(textureRegion));
            }
        } else {
            // Set the cell to null first!
            cell.setTile(null);

            // Now fix surrounding auto-tiles
            autoTiler.eraseTile(tileX, tileY);
        }
    }

    private void populateLayer(TiledMapTileLayer tileLayer) {
        for (int x = 0; x < tileLayer.getWidth(); x++) {
            for (int y = 0; y < tileLayer.getHeight(); y++) {
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
                tileLayer.setCell(x, y, cell);
            }
        }
    }
}
