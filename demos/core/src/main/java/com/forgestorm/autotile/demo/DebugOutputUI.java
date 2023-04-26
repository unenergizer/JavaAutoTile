package com.forgestorm.autotile.demo;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.kotcrab.vis.ui.widget.VisTable;

public class DebugOutputUI extends VisTable implements BuildActor {

    @Override
    public Actor buildActor(StageHandler stageHandler) {

        return this;
    }
}
