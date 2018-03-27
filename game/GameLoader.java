package com.mygdx.game;

/**
 * GameLoader class tells the B2dMdel class, which file to open.
 *
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 25.8.2017.
 */

public class GameLoader {

    /**
     * B2dModel class uses this variable to determine if it should load a file.
     */
    public static boolean wasLoaded = false;
    /**
     * File name of the loadable file.
     */
    public static String fileName = "";

    /**
     * Returns the file name of the file which needs to be loaded.
     *
     * @return  the file's name
     */
    public static String getName(){
        wasLoaded = false;
        return fileName;
    }
}
