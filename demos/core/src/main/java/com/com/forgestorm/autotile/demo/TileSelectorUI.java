package com.forgestorm.autotile.demo;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.forgestorm.autotile.AutoTiler;
import com.kotcrab.vis.ui.widget.VisCheckBox;
import com.kotcrab.vis.ui.widget.VisImageButton;
import com.kotcrab.vis.ui.widget.VisTable;
import com.kotcrab.vis.ui.widget.VisWindow;

public class TileSelectorUI extends VisWindow implements BuildActor {

    public TileSelectorUI() {
        super("Tile Selection:", true);
    }

    @Override
    public Actor buildActor(StageHandler stageHandler) {

        final MapHandler mapHandler = stageHandler.getMapHandler();
        final TextureAtlas textureAtlas = mapHandler.getTextureAtlas();

        final String water = "BW16=beach-water-frame-01=255";
        final String path = "BW16=grass-sand=0";
        final String wall = "BW4=brick-wall-building-h32=0";
        final String table = "BW16=iron-table=0";
        final String lillyPad = "lillypad-01";
        final String tree = "tree-medium-04";

        TextureRegion textureRegionWater = textureAtlas.findRegion(water);
        TextureRegion textureRegionPath = textureAtlas.findRegion(path);
        TextureRegion textureRegionWall = textureAtlas.findRegion(wall);
        TextureRegion textureRegionTable = textureAtlas.findRegion(table);
        TextureRegion textureRegionLillyPad = textureAtlas.findRegion(lillyPad);
        TextureRegion textureRegionTree = textureAtlas.findRegion(tree);

        TextureRegionDrawable textureRegionDrawableWater = new TextureRegionDrawable(textureRegionWater);
        TextureRegionDrawable textureRegionDrawablePath = new TextureRegionDrawable(textureRegionPath);
        TextureRegionDrawable textureRegionDrawableWall = new TextureRegionDrawable(textureRegionWall);
        TextureRegionDrawable textureRegionDrawableTable = new TextureRegionDrawable(textureRegionTable);
        TextureRegionDrawable textureRegionDrawableLillyPad = new TextureRegionDrawable(textureRegionLillyPad);
        TextureRegionDrawable textureRegionDrawableTree = new TextureRegionDrawable(textureRegionTree);

        final int minSize = 32;
        textureRegionDrawableWater.setMinSize(minSize, minSize);
        textureRegionDrawablePath.setMinSize(minSize, minSize);
        textureRegionDrawableWall.setMinSize(minSize, minSize);
        textureRegionDrawableTable.setMinSize(minSize, minSize);
        textureRegionDrawableLillyPad.setMinSize(minSize, minSize);
        textureRegionDrawableTree.setMinSize(minSize, minSize);

        VisImageButton waterButton = new VisImageButton(textureRegionDrawableWater);
        VisImageButton pathButton = new VisImageButton(textureRegionDrawablePath);
        VisImageButton wallButton = new VisImageButton(textureRegionDrawableWall);
        VisImageButton tableButton = new VisImageButton(textureRegionDrawableTable);
        VisImageButton lillyPadButton = new VisImageButton(textureRegionDrawableLillyPad);
        VisImageButton treeButton = new VisImageButton(textureRegionDrawableTree);

        waterButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapHandler.setTileName(water);
                mapHandler.setActiveLayer(0);
            }
        });

        pathButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapHandler.setTileName(path);
                mapHandler.setActiveLayer(0);
            }
        });

        wallButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapHandler.setTileName(wall);
                mapHandler.setActiveLayer(1);
            }
        });

        tableButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapHandler.setTileName(table);
                mapHandler.setActiveLayer(1);
            }
        });

        lillyPadButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapHandler.setTileName(lillyPad);
                mapHandler.setActiveLayer(1);
            }
        });

        treeButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                mapHandler.setTileName(tree);
                mapHandler.setActiveLayer(1);
            }
        });

        AutoTiler autoTiler = stageHandler.getMapHandler().getAutoTiler();

        VisCheckBox fixNeighborTiles = new VisCheckBox("Fix Neighbor Tiles");
        fixNeighborTiles.setChecked(autoTiler.isFixNeighborTiles());
        fixNeighborTiles.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                autoTiler.setFixNeighborTiles(fixNeighborTiles.isChecked());
            }
        });

        VisTable actorTable = new VisTable(true);
        actorTable.add(waterButton);
        actorTable.add(pathButton);
        actorTable.add(wallButton);
        actorTable.add(tableButton);
        actorTable.add(lillyPadButton);
        actorTable.add(treeButton);

        add(actorTable).row();
        add(fixNeighborTiles);

        pack();
        setPosition(15, 15);
        return this;
    }
}
