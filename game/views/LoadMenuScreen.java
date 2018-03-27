package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.GameLoader;
import com.mygdx.game.Internationalization;
import com.mygdx.game.ViivaRaidaaja;
import com.mygdx.game.controller.KeyboardController;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * LoadMenuScreen class is where the user can choose which save should be loaded.
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 23.8.2017.
 */

public class LoadMenuScreen implements Screen {

    private ViivaRaidaaja parent;
    private KeyboardController controller;
    /**
     * A scene2D graph containing all the actors
     */
    private Stage stage;
    /**
     * Initializes the table which contains the exit button
     */
    private Table leftTable = new Table();
    /**
     * Initializes the UI table
     */
    private Table table = new Table();
    /**
     * Initializes the UI skin
     */
    private Skin skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));

    /**
     * Initializes the stage and the keyboard controller
     *
     * @param game the game instance
     */
    public LoadMenuScreen(ViivaRaidaaja game){
        parent = game;
        stage = new Stage(new ScreenViewport());
        controller = new KeyboardController();
    }

    /**
     * Fills the load menu table and listeners for the buttons.
     * Initializes buttons so that their text changes with the screen and adds them to the table.
     *
     */
    @Override
    public void show() {
        /*
         * Input multiplexer is created so that multiple inputprocessors can be used simultaneously.
         */
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(controller);
        Gdx.input.setInputProcessor(inputMultiplexer);

        I18NBundle bundle = Internationalization.getBundle();

        /*
         * Set true if table debug lines are needed
         */
        //table.setDebug(false);
        /*
         * Add all tables to stage. Last added will always be placed on top.
         */
        stage.addActor(table);
        stage.addActor(leftTable);
        /*
         * Initialize the back button
         */
        TextButton back = new TextButton(bundle.format("back"), skin);
        leftTable.setFillParent(true);
        /*
         * Set true if table debug lines are needed
         */
        //leftTable.setDebug(false);
        leftTable.add(back).fillX().uniformX();

        table.setFillParent(true);
        /*
         * Fetch the file from the saves folder and create a button for each one
         */
        FileHandle[] files = Gdx.files.internal("saves/").list();
        for(final FileHandle file: files) {
            String name = file.name();
            String parts[] = name.split("\\.");
            TextButton lb = new TextButton(parts[0], skin);
            table.add(lb).fillX().uniformX();
            table.row();

            /*
             * Add listener to the button
             */
            lb.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    leftTable.clear();
                    table.clear();
                    GameLoader.fileName = file.name();
                    GameLoader.wasLoaded = true;
                    parent.changeScreen(ViivaRaidaaja.APPLICATION);
                }
            });
        }

        /*
         * Add listener to the back button
         */
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                leftTable.clear();
                table.clear();
                parent.changeScreen(ViivaRaidaaja.MENU);
            }
        });

    }

    /**
     * Renders the screen
     *
     * @param delta delta
     */
    @Override
    public void render(float delta) {
        /*
         * Sets the screen color according to preferences
         */
        Gdx.gl.glClearColor(parent.getPreferences().getBgR()/255f, parent.getPreferences().getBgG()/255f, parent.getPreferences().getBgB()/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Math.min(graphics.getDeltaTime(), 1 / 30f));
        /*
         * Draw the stage
         */
        stage.draw();
        /*
         * If backspace is pressed, the screen is changed to menu
         */
        if(controller.backSpaceDown){
            controller.backSpaceDown = false;
            parent.changeScreen(ViivaRaidaaja.MENU);
        }
        /*
         * The load mmenu table can be scrolled up and down
         */
        if(controller.isScrolledUp){
            table.setPosition(table.getX(), table.getY() + 10);
            controller.isScrolledUp = false;
        }
        else if(controller.isScrolledDown){
            table.setPosition(table.getX(), table.getY() - 10);
            controller.isScrolledDown = false;
        }
    }

    /**
     * Keep the back button on the left upper corner when the window is resized.
     * @param width  width
     * @param height height
     */
    @Override
    public void resize(int width, int height) {
        int windowWidth = graphics.getWidth();
        int windowHeight = graphics.getHeight();
        leftTable.setPosition(windowWidth - windowWidth * 1.5f + 90, windowHeight - windowHeight/2 - 40);
        stage.getViewport().update(width, height, true);
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
