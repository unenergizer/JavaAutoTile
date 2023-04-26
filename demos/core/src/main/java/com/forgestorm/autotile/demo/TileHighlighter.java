package com.forgestorm.autotile.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class TileHighlighter extends ApplicationAdapter {

    private final Camera camera;
    private final GameInput gameInput;
    private ShapeRenderer shapeRenderer;

    public TileHighlighter(Camera camera, GameInput gameInput) {
        this.camera = camera;
        this.gameInput = gameInput;
    }

    @Override
    public void create() {
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void render() {
        int x = gameInput.getMouseX() * MapHandler.TILE_SIZE;
        int y = gameInput.getMouseY() * MapHandler.TILE_SIZE;

        if (x < 0 || x > MapHandler.MAP_WIDTH * MapHandler.TILE_SIZE) return;
        if (y < 0 || y > MapHandler.MAP_HEIGHT * MapHandler.TILE_SIZE) return;

        // Draw red selection square.
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.rect(x, y, MapHandler.TILE_SIZE, MapHandler.TILE_SIZE);
        shapeRenderer.end();
    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }
}
