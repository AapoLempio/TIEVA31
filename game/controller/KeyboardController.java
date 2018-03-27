package com.mygdx.game.controller;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

/**
 *  Keyboard controller class. Checks for user keyboard and mouse inputs.
 *
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 9.8.2017.
 */

public class KeyboardController implements InputProcessor {
    /**
     * Mouse button states
     */
    public boolean isMouse1Down, isMouse2Down, isMouse3Down, isDragged, isScrolledUp, isScrolledDown;
    /**
     * Keyboard key states
     */
    public boolean rDown, spaceDown, ctrlDown, zDown, lShiftDown, sDown, enterDown, upDown, downDown, leftDown, rightDown, backSpaceDown;
    /**
     * The cursors location on the screen
     */
    public Vector2 mouseLocation = new Vector2(0,0);

    /**
     * Sets the pressed key's down state as true
     *
     * @param keycode   the keyboard keycode of the key that was pressed
     * @return          was a key processed
     */
    @Override
    public boolean keyDown(int keycode) {
        boolean keyProcessed = false;
        switch (keycode){
            case Input.Keys.UP:
                upDown = true;
                keyProcessed  = true;
                break;
            case Input.Keys.DOWN:
                downDown = true;
                keyProcessed  = true;
                break;
            case Input.Keys.LEFT:
                leftDown = true;
                keyProcessed  = true;
                break;
            case Input.Keys.RIGHT:
                rightDown = true;
                keyProcessed  = true;
                break;
            case Input.Keys.R:
                rDown = true;
                keyProcessed  = true;
                break;
            case Input.Keys.SPACE:
                spaceDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.CONTROL_LEFT:
                ctrlDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.Z:
                zDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.SHIFT_LEFT:
                lShiftDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                sDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.ENTER:
                enterDown = true;
                keyProcessed = true;
                break;
            case Input.Keys.BACKSPACE:
                backSpaceDown = true;
                keyProcessed = true;
                break;
        }
        return keyProcessed;
    }

    /**
     * Sets the pressed key's down state as false
     *
     * @param keycode   the keyboard keycode of the key that was pressed
     * @return          was a key processed
     */

    @Override
    public boolean keyUp(int keycode) {
        boolean keyProcessed = false;
        switch (keycode){
            case Input.Keys.UP:
                upDown = false;
                keyProcessed  = true;
                break;
            case Input.Keys.DOWN:
                downDown = false;
                keyProcessed  = true;
                break;
            case Input.Keys.LEFT:
                leftDown = false;
                keyProcessed  = true;
                break;
            case Input.Keys.RIGHT:
                rightDown = false;
                keyProcessed  = true;
                break;
            case Input.Keys.R:
                rDown = false;
                keyProcessed  = true;
                break;
            case Input.Keys.SPACE:
                spaceDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.CONTROL_LEFT:
                ctrlDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.Z:
                zDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.SHIFT_LEFT:
                lShiftDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.S:
                sDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.ENTER:
                enterDown = false;
                keyProcessed = true;
                break;
            case Input.Keys.BACKSPACE:
                backSpaceDown = false;
                keyProcessed = true;
                break;

        }
        return keyProcessed;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * Sets the pressed mouse button's down state as true  and gets the mouse's location
     *
     * @param screenX   cursors x-coordinate on screen
     * @param screenY   cursors y-coordinate on screen
     * @param pointer   pointer
     * @param button    pressed mouse button
     * @return          false
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if(button == 0) {
            isMouse1Down = true;
        }
        else if(button == 1){
            isMouse2Down = true;
        }
        else if(button == 2){
            isMouse3Down = true;
        }
        mouseLocation = new Vector2(screenX, screenY);
        return false;
    }

    /**
     * Sets the pressed mouse button's down state as true and gets the mouse's location
     *
     * @param screenX   cursors x-coordinate on screen
     * @param screenY   cursors y-coordinate on screen
     * @param pointer   pointer
     * @param button    pressed mouse button
     * @return          false
     */

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        isDragged = false;
        if(button == 0){
            isMouse1Down = false;
        }
        else if(button == 1){
            isMouse2Down = false;
        }
        else if(button == 2){
            isMouse3Down = false;
        }
        mouseLocation = new Vector2(screenX, screenY);
        return false;
    }

    /**
     *  Sets the pressed mouse's dragged state as true and gets the mouse's location
     *
     * @param screenX   cursors x-coordinate on screen
     * @param screenY   cursors y-coordinate on screen
     * @param pointer   pointer
     * @return          false
     */

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        isDragged = true;
        mouseLocation = new Vector2(screenX, screenY);
        return false;
    }

    /**
     * Sets the pressed mouse's dragged state as false and gets the mouse's location
     *
     * @param screenX   cursors x-coordinate on screen
     * @param screenY   cursors y-coordinate on screen
     * @return          false
     */

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        mouseLocation = new Vector2(screenX, screenY);
        return false;
    }

    /**
     * Sets the scrolled up and scrolled down states accordingly
     *
     * @param amount    the scrolling direction
     * @return          false
     */

    @Override
    public boolean scrolled(int amount) {
        if(amount == 1){
            isScrolledUp = true;
            isScrolledDown = false;
        }
        else if(amount == -1){
            isScrolledUp = false;
            isScrolledDown = true;
        }
        return false;
    }

}
