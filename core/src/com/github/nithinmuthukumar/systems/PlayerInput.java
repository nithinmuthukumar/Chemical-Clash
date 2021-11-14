package com.github.nithinmuthukumar.systems;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.CState;


import static com.github.nithinmuthukumar.Globals.cmState;
import static com.github.nithinmuthukumar.Globals.player;

public class PlayerInput implements InputProcessor{
    @Override
    public boolean keyDown(int keycode) {
        CState state = cmState.get(player.getEntity());

        switch (keycode){

            case Input.Keys.W:
            case Input.Keys.S:
                state.action = Action.MOVE;
                break;
            case Input.Keys.A:
                state.action = Action.MOVE;
                state.direction = Direction.LEFT;
                break;
            case Input.Keys.D:
                state.action = Action.MOVE;
                state.direction = Direction.RIGHT;




        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        CState state = cmState.get(player.getEntity());
        switch (keycode){
            case Input.Keys.A:
            case Input.Keys.D:
            case Input.Keys.W:
            case Input.Keys.S:
                state.action = Action.IDLE;
                break;


        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
