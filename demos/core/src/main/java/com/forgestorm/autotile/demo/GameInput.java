package com.forgestorm.autotile.demo;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector3;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GameInput extends InputAdapter {

    private final OrthographicCamera camera;
    private final MapHandler mapHandler;
    @Getter
    private int mouseX, mouseY;

    /**
     * A {@link Vector3} that is used to transform screen coordinates
     * into game map coordinates.
     */
    private final Vector3 tempVec = new Vector3();

    /**
     * The button that was pressed on the mouse.
     */
    private int button;

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == Input.Buttons.LEFT) {
            mapHandler.setTile(mouseX, mouseY, false);
            this.button = button;
            return true;
        } else if (button == Input.Buttons.RIGHT) {
            mapHandler.setTile(mouseX, mouseY, true);
            this.button = button;
            return true;
        }

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        setMouseCoordinates(screenX, screenY);

        if (button == Input.Buttons.LEFT) {
            mapHandler.setTile(mouseX, mouseY, false);
            return true;
        } else if (button == Input.Buttons.RIGHT) {
            mapHandler.setTile(mouseX, mouseY, true);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        setMouseCoordinates(screenX, screenY);
        return false;
    }

    /**
     * Updates the main mouseX and mouseY of the {@link AutoTileDemo}.
     *
     * @param screenX The X coordinate of a mouse click or movement.
     * @param screenY The Y coordinate of a mouse click or movement.
     */
    private void setMouseCoordinates(int screenX, int screenY) {
        Vector3 vector3 = camera.unproject(tempVec.set(screenX, screenY, 0));
        mouseX = (int) vector3.x / MapHandler.TILE_SIZE;
        mouseY = (int) vector3.y / MapHandler.TILE_SIZE;
    }
}
