package com.mygdx.game;


import com.badlogic.gdx.Game;
import com.mygdx.game.views.LoadMenuScreen;
import com.mygdx.game.views.LoadingScreen;
import com.mygdx.game.views.MainScreen;
import com.mygdx.game.views.MenuScreen;
import com.mygdx.game.views.SettingsScreen;

/**
 * The game class. Sets the preferences and changes the screen.
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 */

public class ViivaRaidaaja extends Game {

    private SettingsScreen settingsScreen;
    private MenuScreen menuScreen;
    private MainScreen mainScreen;
    private LoadMenuScreen loadMenuScreen;

    private AppPreferences preferences;

    /*
     * Screen cases
     */
    public final static int MENU = 0;
    public final static int SETTINGS = 1;
    public final static int APPLICATION = 2;
    public final static int LOADMENU = 3;

    /**
     * Initializes the loading screen and the preferences
     */
    @Override
    public void create() {
        LoadingScreen loadingScreen = new LoadingScreen(this);
        preferences = new AppPreferences();
        setScreen(loadingScreen);
    }

    /**
     * Switches the screen to the one that was specified.
     *
     * @param screen    the screen case that is to be shown
     */
    public void changeScreen(int screen){
        switch(screen){
            case MENU:
                if(menuScreen == null) menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;
            case SETTINGS:
                if(settingsScreen == null) settingsScreen = new SettingsScreen(this);
                this.setScreen(settingsScreen);
                break;
            case APPLICATION:
                if(mainScreen == null) mainScreen = new MainScreen(this);
                this.setScreen(mainScreen);
                break;
            case LOADMENU:
                if(loadMenuScreen == null) loadMenuScreen = new LoadMenuScreen(this);
                this.setScreen(loadMenuScreen);
                break;
        }
    }

    /**
     * Gets the preferences
     *
     * @return  the users preferences
     */
    public AppPreferences getPreferences(){
        return this.preferences;
    }


}
