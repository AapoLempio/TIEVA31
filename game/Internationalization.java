package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

/**
 * Internationalization class for the internationalization of the game's UI.
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 27.8.2017.
 */

public class Internationalization {

    /**
     * Stores the wanted locale suffix e.g. "fi" or "en"
     */
    public static String localeSuf = "";

    /**
     * A variable that the settings screen uses to know if it should change the language immediately in the screen
     */
    public static boolean localeChanged = false;

    /**
     * Returns the UIBundle with the correct locale
     *
     * @return  the bundle with the correct locale
     */
    public static I18NBundle getBundle(){

        FileHandle baseFileHandle = Gdx.files.internal("i18n/UIBundle");
        Locale locale = new Locale(localeSuf);

        return I18NBundle.createBundle(baseFileHandle, locale);
    }
}
