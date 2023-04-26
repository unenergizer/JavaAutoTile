package com.forgestorm.autotile.demo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.kotcrab.vis.ui.VisUI;
import lombok.Getter;

public class StageHandler extends ApplicationAdapter {
    @Getter
    private final MapHandler mapHandler;
    @Getter
    private Stage stage;

    public StageHandler(MapHandler mapHandler) {
        this.mapHandler = mapHandler;
    }

    @Override
    public void create() {
        VisUI.load(VisUI.SkinScale.X1);
        stage = new Stage();

        // Add and build all the user interface elements
        stage.addActor(new TileSelectorUI().buildActor(this));
        stage.addActor(new DebugOutputUI().buildActor(this));

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        VisUI.dispose();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }
}
