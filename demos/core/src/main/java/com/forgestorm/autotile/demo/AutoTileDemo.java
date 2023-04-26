package com.forgestorm.autotile.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class AutoTileDemo extends ApplicationAdapter {

    public static final int WINDOW_WIDTH = 1024;
    public static final int WINDOW_HEIGHT = 768;

    private StageHandler stageHandler;
    private MapHandler mapHandler;
    private TileHighlighter tileHighlighter;
    private Viewport viewport;

    @Override
    public void create() {
        OrthographicCamera camera = new OrthographicCamera();
        camera.zoom = .25f; // Zoom in, so it's easy to see the map.
        camera.setToOrtho(false);
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        // Initialize the map handler
        mapHandler = new MapHandler(camera);
        mapHandler.create();

        GameInput gameInput = new GameInput(camera, mapHandler);

        tileHighlighter = new TileHighlighter(camera, gameInput);
        tileHighlighter.create();

        stageHandler = new StageHandler(mapHandler);
        stageHandler.create();

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stageHandler.getStage(), gameInput);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void render() {
        viewport.update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        ScreenUtils.clear(112 / 255f, 161 / 255f, 94 / 255f, 1);
        mapHandler.render();
        tileHighlighter.render();
        stageHandler.render();
    }

    @Override
    public void resize(int width, int height) {
        stageHandler.resize(width, height);
    }

    @Override
    public void dispose() {
        mapHandler.dispose();
        tileHighlighter.dispose();
        stageHandler.dispose();
    }
}
