package com.mygdx.game.views;

import com.badlogic.gdx.Screen;
import com.mygdx.game.ViivaRaidaaja;

/**
 * The loading screen class which at the moment only directs the program to the menuscreen
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 9.8.2017.
 */

public class LoadingScreen implements Screen {

    private ViivaRaidaaja parent;

    /**
     *
     * @param game the game instance
     */
    public LoadingScreen(ViivaRaidaaja game){
        parent = game;
    }

    @Override
    public void show() {

    }

    /**
     * Directs the game to menuScreen
     *
     * @param delta delta
     */
    @Override
    public void render(float delta) {
       parent.changeScreen(ViivaRaidaaja.MENU);
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
