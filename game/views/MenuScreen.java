package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Internationalization;
import com.mygdx.game.SpriteHandler;
import com.mygdx.game.ViivaRaidaaja;
import com.mygdx.game.controller.KeyboardController;

import static com.badlogic.gdx.Gdx.graphics;
import static com.badlogic.gdx.graphics.Color.WHITE;
import static com.badlogic.gdx.graphics.Color.YELLOW;

/**
 * MenuScreen class is the main menu
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 8.8.2017.
 */

public class MenuScreen implements Screen {

    private ViivaRaidaaja parent;
    /**
     * A scene2D graph containing all the actors
     */
    private Stage stage;
    /*
     * Menu buttons
     */
    private TextButton newGame;
    private TextButton loadGame;
    private TextButton settings;
    private TextButton exit;

    private KeyboardController controller;
    /**
     * The button index of the active button
     */
    private int activeButton = 0;
    /**
     * Puts the key back "up" so it gets pressed only once
     */
    private boolean downUp, upUp;
    private SpriteBatch sb;
    private Sprite logo;
    /**
     * Table for the menu buttons
     */
    private Table table = new Table();

    /**
     * Initializes the stage, keyboard controller, sprite batch and sprite handler, creates the logo and gets the preferred language
     *
     * @param game the game instance
     */
    public MenuScreen(ViivaRaidaaja game){
        parent = game;
        stage = new Stage(new ScreenViewport());
        controller = new KeyboardController();
        sb = new SpriteBatch();
        SpriteHandler spriteHandler = new SpriteHandler();
        spriteHandler.addSprites();
        logo = spriteHandler.createLogo();
        logo.setScale(0.15f);
        logo.setPosition(-775, 175);
        Internationalization.localeSuf = parent.getPreferences().getLanguage();

    }

    /**
     * Fills the menu table and adds listeners for the buttons.
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
        /*
         * Add table to stage
         */
        stage.addActor(table);
        /*
         * Initialize the skin that the UI uses
         */
        Skin skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));
        /*
         * Gets the language preference
         */
        I18NBundle bundle = Internationalization.getBundle();
        /*
         * Initialize the buttons
         */
        newGame = new TextButton(bundle.format("new_game"), skin);
        loadGame = new TextButton(bundle.format("load_game"), skin);
        settings = new TextButton(bundle.format("settings"), skin);
        exit = new TextButton(bundle.format("exit"), skin);

        /*
         * Set true if table debug lines are needed
         */
        //table.setDebug(false);
        table.setFillParent(true);
        /*
         * Add menu buttons to table
         */
        table.add(newGame).fillX().uniformX();
        table.row();
        table.add(loadGame).fillX().uniformX();
        table.row();
        table.add(settings).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();
        /*
         * Listeners for the buttons
         */
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
        settings.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table.clear();
                parent.changeScreen(ViivaRaidaaja.SETTINGS);
            }
        });
        newGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table.clear();
                parent.changeScreen(ViivaRaidaaja.APPLICATION);
            }
        });
        loadGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table.clear();
                parent.changeScreen(ViivaRaidaaja.LOADMENU);
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
         * Begins sprite batch so that sprites can be drawn
         */
        sb.begin();
        logo.draw(sb);
        sb.end();

        /*
         * Does the keyboard navigation screening
         */
        navigate();

    }

    /**
     * Prevents the UI from resizing with the window
     *
     * @param width  width
     * @param height height
     */

    @Override
    public void resize(int width, int height) {
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

    /**
     * Disposes the stage
     */

    @Override
    public void dispose() {
        stage.dispose();
    }

    /**
     * Highlights the correct button
     * @param button    the button that needs to be highlighted
     */
    private void highlight(String button){
        if(button.equals("newGame"))
            newGame.setColor(YELLOW);
        else
            newGame.setColor(WHITE);
        if(button.equals("loadGame"))
            loadGame.setColor(YELLOW);
        else
            loadGame.setColor(WHITE);
        if(button.equals("settings"))
            settings.setColor(YELLOW);
        else
            settings.setColor(WHITE);
        if(button.equals("exit"))
            exit.setColor(YELLOW);
        else
            exit.setColor(WHITE);
    }

    /**
     * Does the keyboard navigation functions
     */
    private void navigate(){
        /*
         * Determines which button is set active based on which key was pressed
         */
        if(controller.upDown && !upUp && activeButton > 0){
            activeButton--;
        }
        else if(controller.upDown && !upUp && activeButton == 0){
            activeButton = 3;
        }
        else if(controller.downDown && !downUp && activeButton < 3){
            activeButton++;
        }
        else if(controller.downDown && !downUp && activeButton == 3){
            activeButton = 0;
        }
        /*
         * Highlights the next button down
         */
        if(!controller.downDown){
            if(controller.upDown && !upUp && !downUp){
                if(activeButton == 0){
                    highlight("newGame");
                    upUp = true;
                }
                else if(activeButton == 1){
                    highlight("loadGame");
                    upUp = true;
                }
                else if(activeButton == 2){
                    highlight("settings");
                    upUp = true;
                }
                else if(activeButton == 3){
                    highlight("exit");
                    upUp = true;
                }
            }
            downUp = false;
        }
        /*
         * Highlights the next button up
         */
        if(!controller.upDown){
            if(controller.downDown && !downUp && !upUp){
                if(activeButton == 0){
                    highlight("newGame");
                    downUp = true;
                }
                else if(activeButton == 1){
                    highlight("loadGame");
                    downUp = true;
                }
                else if(activeButton == 2){
                    highlight("settings");
                    downUp = true;
                }
                else if(activeButton == 3){
                    highlight("exit");
                    downUp = true;
                }
            }
            upUp = false;
        }
        /*
         * If enter was pressed change screen based on active button
         */
        if(controller.enterDown){
            if(activeButton == 0){
                controller.enterDown = false;
                table.clear();
                parent.changeScreen(ViivaRaidaaja.APPLICATION);
            }
            else if(activeButton == 1){
                controller.enterDown = false;
                table.clear();
                parent.changeScreen(ViivaRaidaaja.LOADMENU);
            }
            else if(activeButton == 2){
                controller.enterDown = false;
                table.clear();
                parent.changeScreen(ViivaRaidaaja.SETTINGS);
            }
            else if(activeButton == 3){
                Gdx.app.exit();
            }
        }
    }
}
