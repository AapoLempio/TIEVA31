package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * AppPreferences class updates the users preferences e.g. the language, background color... etc.
 * so that the settings will stay the same even if the app is closed
 *
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 28.8.2017.
 */

public class AppPreferences {
    /*
     * Background color RGB values
     */
    private static final String PREF_BG_R = "bgr";
    private static final String PREF_BG_G = "bgg";
    private static final String PREF_BG_B = "bgb";

    /*
     * Line color RGB values
     */
    private static final String PREF_LINE_R = "lr";
    private static final String PREF_LINE_G = "lg";
    private static final String PREF_LINE_B = "lb";

    private static final String PREF_GRAVITY = "gravity";
    private static final String PREF_LANG = "language";

    private static final String PREFS_NAME = "prefs";

    /**
     * Returns the apps preferences.
     *
     * @return the preferences
     */
    private Preferences getPrefs(){
        return Gdx.app.getPreferences(PREFS_NAME);
    }

    /**
     * Gets the preferred language.
     *
     * @return the language
     */
    public String getLanguage(){
        return getPrefs().getString(PREF_LANG, "en");
    }

    /**
     * Sets the preferred language.
     *
     * @param lang  the preferred language
     */
    public void setLanguage(String lang){
        getPrefs().putString(PREF_LANG, lang);
        getPrefs().flush();
    }

    /**
     * Gets the preferred background color value for red
     *
     * @return the preferred background color value for red
     */
    public float getBgR(){
        return getPrefs().getFloat(PREF_BG_R, 0f);
    }

    /**
     * Sets the preferred background color value for red
     *
     * @param bgr the preferred background color value for red
     */
    public void setBgR(float bgr){
        getPrefs().putFloat(PREF_BG_R, bgr);
        getPrefs().flush();
    }

    /**
     * Gets the preferred background color value for green
     *
     * @return the preferred background color value for green
     */
    public float getBgG(){
        return getPrefs().getFloat(PREF_BG_G, 0f);
    }

    /**
     * Sets the preferred background color value for green
     *
     * @param bgg the preferred background color value for green
     */
    public void setBgG(float bgg){
        getPrefs().putFloat(PREF_BG_G, bgg);
        getPrefs().flush();
    }

    /**
     * Gets the preferred background color value for blue
     *
     * @return the preferred background color value for blue
     */
    public float getBgB(){
        return getPrefs().getFloat(PREF_BG_B, 0f);
    }

    /**
     * Sets the preferred background color value for blue
     *
     * @param bgb the preferred background color value for blue
     */
    public void setBgB(float bgb){
        getPrefs().putFloat(PREF_BG_B, bgb);
        getPrefs().flush();
    }

    /**
     * Gets the preferred line color value for red
     *
     * @return the preferred line color value for red
     */
    public float getLineR(){
        return getPrefs().getFloat(PREF_LINE_R, 0f);
    }

    /**
     * Sets the preferred line color value for red
     *
     * @param lr the preferred line color value for red
     */
    public void setLineR(float lr){
        getPrefs().putFloat(PREF_LINE_R, lr);
        getPrefs().flush();
    }

    /**
     * Gets the preferred line color value for green
     *
     * @return the preferred line color value for green
     */
    public float getLineG(){
        return getPrefs().getFloat(PREF_LINE_G, 0f);
    }

    /**
     * Sets the preferred line color value for green
     *
     * @param lg the preferred line color value for green
     */
    public void setLineG(float lg){
        getPrefs().putFloat(PREF_LINE_G, lg);
        getPrefs().flush();
    }

    /**
     * Gets the preferred line color value for blue
     *
     * @return the preferred line color value for blue
     */
    public float getLineB(){
        return getPrefs().getFloat(PREF_LINE_B, 0f);
    }

    /**
     * Sets the preferred line color value for blue
     *
     * @param lb the preferred line color value for blue
     */
    public void setLineB(float lb){
        getPrefs().putFloat(PREF_LINE_B, lb);
        getPrefs().flush();
    }

    /**
     * Gets the preferred gravity value
     *
     * @return the preferred gravity value
     */

    public float getGravity(){
        return getPrefs().getFloat(PREF_GRAVITY, 10f);
    }

    /**
     * Sets the preferred gravity value
     *
     * @param gravity the preferred gravity value
     */
    public void setGravity(float gravity){
        getPrefs().putFloat(PREF_GRAVITY, gravity);
        getPrefs().flush();
    }
}
