package com.mygdx.game.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Internationalization;
import com.mygdx.game.ViivaRaidaaja;
import com.mygdx.game.controller.KeyboardController;

import static com.badlogic.gdx.Gdx.graphics;

/**
 * SettingsScreen class is where the user can adjust the preferences
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 8.8.2017.
 */

public class SettingsScreen implements Screen {

    private ViivaRaidaaja parent;
    /**
     * A scene2D graph containing all the actors
     */
    private Stage stage;
    /**
     * Initialize the table for the back button
     */
    private Table leftTable = new Table();
    /**
     * Initialize the table for the menu buttons
     */
    private Table table = new Table();
    /*
     * Initialize the skin that the UI uses
     */
    private Skin skin = new Skin(Gdx.files.internal("skin/freezing-ui.json"));

    private TextButton back;
    /*
     * The language dropdown menu and language locales and label
     */
    private SelectBox<String> lang = new SelectBox<String>(skin);
    private String langs[] = new String[]{"en", "fi"};
    private Label langLabel;
    /*
     * The background color sliders and labels
     */
    private Slider bgColorSliderR;
    private Slider bgColorSliderG;
    private Slider bgColorSliderB;
    private Label bgNumLabelR;
    private Label bgNumLabelG;
    private Label bgNumLabelB;
    private Label bgColorLabel;
    /*
     * The line color sliders and labels
     */
    private Slider lineColorSliderR;
    private Slider lineColorSliderG;
    private Slider lineColorSliderB;
    private Label lineNumLabelR;
    private Label lineNumLabelG;
    private Label lineNumLabelB;
    private Label lineColorLabel;
    /*
     * The gravity slider and labels
     */
    private Label gravityLabel;
    private Label gravityNumLabel;
    private Slider gravitySlider;

    private KeyboardController controller;

    /**
     * Initializes the stage and controller
     *
     * @param game  the game instance
     */
    public SettingsScreen(ViivaRaidaaja game){
        parent = game;
        stage = new Stage(new ScreenViewport());
        controller = new KeyboardController();
    }

    /**
     * Fills the settings table and listeners for the actors.
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
        lang.setItems(langs);
        lang.setSelected(parent.getPreferences().getLanguage());

        bgColorSliderR = new Slider(0,255, 1,false,skin);
        bgColorSliderG = new Slider(0,255, 1,false,skin);
        bgColorSliderB = new Slider(0,255, 1,false,skin);
        /*
         * Sets sliders to the preferred value
         */
        bgColorSliderR.setValue(parent.getPreferences().getBgR());
        bgColorSliderG.setValue(parent.getPreferences().getBgG());
        bgColorSliderB.setValue(parent.getPreferences().getBgB());

        lineColorSliderR = new Slider(0,255, 1,false,skin);
        lineColorSliderG = new Slider(0,255, 1,false,skin);
        lineColorSliderB = new Slider(0,255, 1,false,skin);
        /*
         * Sets sliders to the preferred value
         */
        lineColorSliderR.setValue(parent.getPreferences().getLineR());
        lineColorSliderG.setValue(parent.getPreferences().getLineG());
        lineColorSliderB.setValue(parent.getPreferences().getLineB());

        langLabel = new Label(bundle.format("choose_language"),skin);
        bgColorLabel = new Label(bundle.format("choose_bg_color"),skin);
        /*
         * Sets labels to the preferred values
         */
        bgNumLabelR = new Label("R: " + String.valueOf(parent.getPreferences().getBgR()),skin);
        bgNumLabelG = new Label("G: " + String.valueOf(parent.getPreferences().getBgG()),skin);
        bgNumLabelB = new Label("B: " + String.valueOf(parent.getPreferences().getBgB()),skin);

        lineColorLabel = new Label(bundle.format("choose_line_color"),skin);
        /*
         * Sets labels to the preferred values
         */
        lineNumLabelR = new Label("R: " + String.valueOf(parent.getPreferences().getLineR()),skin);
        lineNumLabelG = new Label("G: " + String.valueOf(parent.getPreferences().getLineG()),skin);
        lineNumLabelB = new Label("B: " + String.valueOf(parent.getPreferences().getLineB()),skin);

        gravitySlider = new Slider(1,100, 0.5f,false,skin);
        /*
         * Sets slider to the preferred value
         */
        gravitySlider.setValue(parent.getPreferences().getGravity());
        gravityLabel = new Label(bundle.format("choose_gravity"),skin);
        gravityNumLabel = new Label(String.valueOf(parent.getPreferences().getGravity()),skin);

        back = new TextButton(bundle.format("back"), skin);
        leftTable.setFillParent(true);
        /*
         * Set true if table debug lines are needed
         */
        //leftTable.setDebug(false);
        stage.addActor(leftTable);
        leftTable.add(back).fillX().uniformX();
        leftTable.row();

        /*
         * Set true if table debug lines are needed
         */
        //table.setDebug(false);
        /*
         * Add table to stage
         */
        stage.addActor(table);
        table.setFillParent(true);
        /*
         * Add actors to table
         */
        table.add(langLabel).fillX().uniformX();
        table.row();
        table.add(lang).fillX().uniformX();
        table.row();
        table.add(bgColorLabel).fillX().uniformX();
        table.row();
        table.add(bgColorSliderR).fillX().uniformX();
        table.add(bgNumLabelR).fillX().uniformX();
        table.row();
        table.add(bgColorSliderG).fillX().uniformX();
        table.add(bgNumLabelG).fillX().uniformX();
        table.row();
        table.add(bgColorSliderB).fillX().uniformX();
        table.add(bgNumLabelB).fillX().uniformX();
        table.row();
        table.add(lineColorLabel).fillX().uniformX();
        table.row();
        table.add(lineColorSliderR).fillX().uniformX();
        table.add(lineNumLabelR).fillX().uniformX();
        table.row();
        table.add(lineColorSliderG).fillX().uniformX();
        table.add(lineNumLabelG).fillX().uniformX();
        table.row();
        table.add(lineColorSliderB).fillX().uniformX();
        table.add(lineNumLabelB).fillX().uniformX();
        table.row();
        table.add(gravityLabel).fillX().uniformX();
        table.row();
        table.add(gravitySlider).fillX().uniformX();
        table.add(gravityNumLabel).fillX().uniformX();
        table.row();

        /*
         * Create listeners for the settings actors
         */
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                table.clear();
                leftTable.clear();
                parent.changeScreen(ViivaRaidaaja.MENU);
            }
        });
        lang.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if(lang.getSelected().equals("en")){
                    Internationalization.localeSuf = "en";
                    parent.getPreferences().setLanguage("en");
                    Internationalization.localeChanged = true;
                }
                else if(lang.getSelected().equals("fi")){
                    Internationalization.localeSuf = "fi";
                    parent.getPreferences().setLanguage("fi");
                    Internationalization.localeChanged = true;
                }
                if(Internationalization.localeChanged){
                    I18NBundle bundle = Internationalization.getBundle();
                    back.setText(bundle.format("back"));
                    bgColorLabel.setText(bundle.format("choose_bg_color"));
                    lineColorLabel.setText(bundle.format("choose_line_color"));
                    langLabel.setText(bundle.format("choose_language"));
                    gravityLabel.setText(bundle.format("choose_gravity"));
                    Internationalization.localeChanged = false;
                }
            }
        });
        bgColorSliderR.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setBgR(bgColorSliderR.getValue());
                bgNumLabelR.setText("R: " + String.valueOf(bgColorSliderR.getValue()));
            }
        });
        bgColorSliderG.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setBgG(bgColorSliderG.getValue());
                bgNumLabelG.setText("G: " + String.valueOf(bgColorSliderG.getValue()));
            }
        });
        bgColorSliderB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setBgB(bgColorSliderB.getValue());
                bgNumLabelB.setText("B: " + String.valueOf(bgColorSliderB.getValue()));
            }
        });
        lineColorSliderR.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setLineR(lineColorSliderR.getValue());
                lineNumLabelR.setText("R: " + String.valueOf(lineColorSliderR.getValue()));
            }
        });
        lineColorSliderG.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setLineG(lineColorSliderG.getValue());
                lineNumLabelG.setText("G: " + String.valueOf(lineColorSliderG.getValue()));
            }
        });
        lineColorSliderB.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setLineB(lineColorSliderB.getValue());
                lineNumLabelB.setText("B: " + String.valueOf(lineColorSliderB.getValue()));
            }
        });
        gravitySlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.getPreferences().setGravity(gravitySlider.getValue());
                gravityNumLabel.setText(String.valueOf(gravitySlider.getValue()));
            }
        });
    }

    /**
     *
     * @param delta delta
     */
    @Override
    public void render(float delta) {
        /*
         * Sets the screen color according to preferences
         */
        Gdx.gl.glClearColor(bgColorSliderR.getValue()/255, bgColorSliderG.getValue()/255, bgColorSliderB.getValue()/255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        /*
         * Sets the line color according to preferences and shows the change in the title
         */
        lineColorLabel.setColor(lineColorSliderR.getValue()/255, lineColorSliderG.getValue()/255, lineColorSliderB.getValue()/255, 1);
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
    }

    /**
     * Keep the back button on the left upper corner when the window is resized.
     *
     * @param width  width
     * @param height height
     */
    @Override
    public void resize(int width, int height) {
        int windowWidth = graphics.getWidth();
        int windowHeight = graphics.getHeight();
        table.setPosition(50, 0);
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

    /**
     *
     */
    @Override
    public void dispose() {

    }

}
