package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.B2dModel;
import com.mygdx.game.Internationalization;
import com.mygdx.game.SpriteHandler;
import com.mygdx.game.ViivaRaidaaja;
import com.mygdx.game.controller.KeyboardController;

import java.util.Date;

import javax.swing.event.ChangeEvent;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * MainScreen class is where the game itself is
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 8.8.2017.
 */

public class MainScreen implements Screen {

    private final ScreenViewport screenViewport;
    private ExtendViewport viewport;
    private OrthographicCamera cam;
    private KeyboardController controller;
    private ViivaRaidaaja parent;
    /**
     * The B2D Model
     */
    private B2dModel model;
    /**
     * For debugging bodies
     */
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch sb;
    /*
     * Initialize the skin that the UI uses
     */
    private Skin skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));
    /*
     * The game UI buttons and text field
     */
    private TextButton play;
    private TextButton pause;
    private TextButton redo;
    private TextButton undo;
    private TextButton clear;
    private TextButton save;
    private TextButton okSave;
    private TextField saveNameField = new TextField("", skin);

    /*
     * Tables in the UI
     */
    private Table table = new Table();
    private Table leftTable = new Table();
    private Table saveTable = new Table();
    /**
     * A scene2D graph containing all the actors
     */
    private Stage stage;
    /**
     * Viewport x size for zooming
     */
    private float vSizeX = 24;
    /**
     * Viewport y size for zooming
     */
    private float vSizeY = 40;

    private boolean paused = true;
    private boolean stopped = false;
    /**
     * Puts the key back "up" so it gets pressed only once
     */
    private boolean spacedown, rdown, ctrlZdown,ctrlSdown;

    private SpriteHandler spriteHandler;

    /**
     * Initializes keyboard controller, camera, viewport, screen viewport, sprite batch, sprite handler, stage, model and debugrenderer
     * @param game the game instance
     */
    public MainScreen(ViivaRaidaaja game){
        parent = game;
        controller = new KeyboardController();
        cam = new OrthographicCamera(32, 24);
        viewport = new ExtendViewport(25, 25, cam);
        screenViewport = new ScreenViewport();
        sb = new SpriteBatch();
        spriteHandler = new SpriteHandler();
        spriteHandler.addSprites();
        stage = new Stage(screenViewport);
        model = new B2dModel(controller, cam);
        debugRenderer = new Box2DDebugRenderer(true, true, true, true, true, true);

    }
    /**
     * Fills the UI tables and adds listeners for the buttons.
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
         * Gets the language preference
         */
        I18NBundle bundle = Internationalization.getBundle();
        /*
         * Initialize buttons and text field
         */
        TextButton exit = new TextButton(bundle.format("exit"), skin);
        TextButton replay = new TextButton(bundle.format("replay"), skin);
        TextButton stop = new TextButton(bundle.format("stop"), skin);
        play = new TextButton(bundle.format("play"), skin);
        pause = new TextButton(bundle.format("pause"), skin);
        redo = new TextButton(bundle.format("redo"), skin);
        undo = new TextButton(bundle.format("undo"), skin);
        clear = new TextButton(bundle.format("clear"), skin);
        save = new TextButton(bundle.format("save"), skin);
        okSave = new TextButton(bundle.format("save"), skin);
        saveNameField.setMessageText(bundle.format("saveAs"));
        float gravity = parent.getPreferences().getGravity();
        model.world.setGravity(new Vector2(0, -gravity));
        /*
         * Set true if table debug lines are needed
         */
        //saveTable.setDebug(false);
        saveTable.setFillParent(true);
        saveTable.add(saveNameField).fillX().uniformX();
        saveTable.row();
        saveTable.add(okSave).fillX().uniformX();
        saveTable.setVisible(false);

        table.setFillParent(true);
        /*
         * Set true if table debug lines are needed
         */
        //table.setDebug(false);

        table.add(play).fillX().uniformX();
        table.row().pad(-65, 0, 0, 0);
        table.add(pause).fillX().uniformX();
        table.row();
        table.add(stop).fillX().uniformX();
        table.row();
        table.add(replay).fillX().uniformX();
        table.row();
        table.add(redo).fillX().uniformX();
        table.row();
        table.add(undo).fillX().uniformX();
        table.row();
        table.add(clear).fillX().uniformX();
        table.row();
        table.add(save).fillX().uniformX();
        /*
         * Set true if table debug lines are needed
         */
        //leftTable.setDebug(false);
        leftTable.setFillParent(true);
        leftTable.add(exit).fillX().uniformX();
        leftTable.row();

        /*
         * Add tables to stage
         */
        stage.addActor(table);
        stage.addActor(leftTable);
        stage.addActor(saveTable);

        /*
         * Add listeners to buttons
         */
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                paused = false;
            }
        });
        pause.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                paused = true;
            }
        });
        stop.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stop();

            }
        });
        replay.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stop();
                paused = false;
            }
        });
        redo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.redo();
            }
        });
        undo.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.undo();
            }
        });
        clear.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.clearWorld();
            }
        });
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                model.clearWorld();
                cam.position.set(0, 0, 0);
                stop();
                table.clear();
                leftTable.clear();
                saveTable.clear();
                model.bodies.clear();
                model.rotationList.clear();
                model.widthList.clear();
                model.undoneBodies.clear();
                model.undoneWidthList.clear();
                model.undoneRotList.clear();
                parent.changeScreen(ViivaRaidaaja.MENU);
            }
        });
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                saveTable.setVisible(true);
                okSave.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        save();
                    }
                });
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

        model.logicStep(delta, paused);
        /*
         * Checks and does the keyboard functions
         */
        doKeyFunctions();
        if(!paused) {
            stopped = false;
            pause.setVisible(true);
            setButtonsVisible(false);
            cam.position.set(model.player.getPosition().x, model.player.getPosition().y, 0);
        }
        else {
            if(stopped){
                stopped = false;
                cam.position.set(0, 0, 0);
            }
            pause.setVisible(false);
            setButtonsVisible(true);

        }
        /*
         * Sets the screen color according to preferences
         */
        Gdx.gl.glClearColor(parent.getPreferences().getBgR()/255f, parent.getPreferences().getBgG()/255f, parent.getPreferences().getBgB()/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        /*
         * Set the camera size to match the zoomed size
         */
        cam.viewportHeight = vSizeX;
        cam.viewportWidth = vSizeY;

        sb.setProjectionMatrix(cam.combined);
        sb.begin();


        Vector2 position = model.player.getPosition();
        float degrees = (float) Math.toDegrees(model.player.getAngle());
        /*
         * Draw rider sprite
         */
        spriteHandler.drawSprite("Rider", position.x, position.y, degrees,sb);
        /*
         * Draw line sprites
         */
        spriteHandler.drawBodySprite(model.bodies, model.widthList, model.rotationList, sb, parent.getPreferences().getLineR()/255f,parent.getPreferences().getLineG()/255f,parent.getPreferences().getLineB()/255f);

        sb.end();
        /*
         * Draw stage
         */
        stage.draw();
        cam.update();
        /*
         * Render debug lines for bodies
         */
        //debugRenderer.render(model.world, cam.combined);

    }
    /**
     * Keeps the UI at the sides of the screen when resizing.
     * @param width  width
     * @param height height
     */
    @Override
    public void resize(int width, int height) {
        float windowWidth = graphics.getWidth();
        float windowHeight = graphics.getHeight();
        table.setPosition(windowWidth /2 - 105, windowHeight /2 - 230);
        leftTable.setPosition(windowWidth - windowWidth * 1.5f + 90, windowHeight /2 - 40);
        viewport.update(width, height, true);
        screenViewport.update(width, height, true);
        /*
         * Center camera on rider when resizing screen
         */
        cam.position.set(model.player.getPosition().x, model.player.getPosition().y, 0);

        sb.setProjectionMatrix(cam.combined);
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
     * Saves the game
     */
    private void save(){
        String saveName;
        if(saveNameField.getText() != null && !saveNameField.getText().trim().isEmpty()){
            saveName = saveNameField.getText();
        }
        else{
            saveName = "" + new Date(TimeUtils.millis());
        }
        model.saveBodies(saveName);
        saveTable.setVisible(false);
    }

    /**
     * Stops the rider and resets it's position, speed and angle
     */
    private void stop(){
        model.player.setTransform(0, 0, 0);
        model.player.setAngularVelocity(0);
        model.player.setLinearVelocity(0, 0);
        paused = true;
        stopped = true;
    }
    /*
     * Checks and does the keyboard functions
     */
    private void doKeyFunctions(){
        /*
         * If ctrl+shift+s is pressed, then open savetable and if oksave is pressed, save the file. If just ctrl+s is pressed, save
         */
        if(controller.sDown && controller.ctrlDown && !ctrlSdown && paused){
            if(controller.lShiftDown){
                saveTable.setVisible(true);
                okSave.addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        save();
                    }
                });
            }
            else{
                save();
            }
            ctrlSdown = true;
        }
        else if(!controller.sDown || !controller.ctrlDown){
            ctrlSdown = false;
        }
        /*
         * If save save "dialog" is visible, then by clicking elsewhere than on the "dialog" it is set invisible
         */
        if(saveTable.isVisible() && controller.isMouse1Down || saveTable.isVisible() && controller.isMouse2Down || saveTable.isVisible() && controller.isMouse3Down){
            saveTable.setVisible(false);
        }
        /*
         * If dragged with middle mouse button, then move the camera to the mouse's direction. Camera
         * movement speed can be adjusted with the drag variable.
         */

        if(controller.isDragged && controller.isMouse3Down){
            float drag = 40;
            cam.position.set(cam.position.x + (model.position.x - cam.position.x)/ drag, cam.position.y + (model.position.y - cam.position.y)/ drag, 0);
        }
        /*
         * If space is pressed set play if paused and pause if not paused
         */
        if(controller.spaceDown && !spacedown){
            if(paused){
                paused = false;
            }
            else if(!paused){
                paused = true;
            }
            spacedown = true;
        }
        else if(!controller.spaceDown){
            spacedown = false;
        }
        /*
         * If r is pressed, then replay
         */
        if(controller.rDown && !rdown){
            if(paused){
                stop();
                paused = false;
            }
            else if(!paused){
                stop();
                paused = false;
            }
            rdown = true;
        }
        else if(!controller.rDown){
            rdown = false;
        }
        /*
         * If ctrl+z pressed undo and redo if ctrl+shift+z is pressed
         */
        if(controller.zDown && controller.ctrlDown && !ctrlZdown && paused){
            if(controller.lShiftDown){
                model.redo();
            }
            else{
                model.undo();
            }
            ctrlZdown = true;
        }
        else if(!controller.zDown || !controller.ctrlDown){
            ctrlZdown = false;
        }
        /*
         * If scrolled up, adjusts the scrolling speed with the cameras size
         */
        if(controller.isScrolledUp){
            if(vSizeX < 20){
                vSizeX += vSizeX/vSizeY;
                vSizeY++;
            }
            else if(vSizeX < 30){
                vSizeX += 2 * vSizeX/vSizeY;
                vSizeY += 2;
            }
            else if(vSizeX < 40){
                vSizeX += 3 * vSizeX/vSizeY;
                vSizeY += 3;
            }
            else{
                vSizeX += 5 * vSizeX/vSizeY;
                vSizeY += 5;
            }
            controller.isScrolledUp = false;
        }
        /*
         * If scrolled down, adjusts the scrolling speed with the cameras size
         */
        if(controller.isScrolledDown && vSizeX > 2){
            if(vSizeX < 20){
                vSizeX -= vSizeX/vSizeY;
                vSizeY--;
            }
            else if(vSizeX < 30){
                vSizeX -= 2 * vSizeX/vSizeY;
                vSizeY -= 2;
            }
            else if(vSizeX < 40){
                vSizeX -= 3 * vSizeX/vSizeY;
                vSizeY -= 3;
            }
            else{
                vSizeX -= 5 * vSizeX/vSizeY;
                vSizeY -= 5;
            }
            controller.isScrolledDown = false;
        }
        /*
         * If backspace is pressed, the screen is changed to menu
         */
        if(controller.backSpaceDown){
            controller.backSpaceDown = false;
            model.clearWorld();
            cam.position.set(0, 0, 0);
            stop();
            dispose();
            parent.changeScreen(ViivaRaidaaja.MENU);
        }


    }

    /**
     * Set the buttons visible or invisible
     * @param visible   are the buttons visible or not
     */

    private void setButtonsVisible(boolean visible){
        play.setVisible(visible);
        redo.setVisible(visible);
        undo.setVisible(visible);
        save.setVisible(visible);
        clear.setVisible(visible);
    }

    @Override
    public void dispose() {
        skin.dispose();
        sb.dispose();
        stage.clear();
        stage.dispose();
        debugRenderer.dispose();
}
}
