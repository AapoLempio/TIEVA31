package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;

/**
 * SaveParser class, that opens requested save files and parses the information into a usable form.
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 22.8.2017.
 */

class SaveParser {

    /**
     * Stores rows that represent the saved bodies' position, width and rotation. One row contains one body's information.
     */
    private static String[] rows = new String[]{};

    /**
     * Returns the body's position data as Vector2.
     *
     * @param fileName  the file's name which needs to be opened
     * @param row       the row on which the body's data has been stored
     * @return          the body's position data as Vector2
     */
    static Vector2 parsePosition(String fileName, int row){

        String x = readFile(fileName, row)[0];
        String y = readFile(fileName, row)[1];

        return new Vector2(Float.valueOf(x), Float.valueOf(y));
    }

    /**
     * Returns the the body's rotation data as float and as degrees.
     *
     * @param fileName  the file's name which needs to be opened
     * @param row       the row on which the body's data has been stored
     * @return          the body's rotation data as float and as degrees
     */

    static float parseRotation(String fileName, int row){

        String num = readFile(fileName, row)[2];

        return Float.valueOf(num);
    }

    /**
     * Returns the body's width data as float
     *
     * @param fileName  the file's name which needs to be opened
     * @param row       the row on which the body's data has been stored
     * @return          the body's width data as float
     */

    static float parseWidth(String fileName, int row){

        String num = readFile(fileName, row)[3];

        return Float.valueOf(num);
    }

    /**
     * If the rows variable's length is zero, then the save file is split into rows that are then saved into the rows variable.
     * Then the rows are split into parts. Each row has four parts:
     * body position x, body position y, body width and body rotation.
     *
     * @param fileName  the file's name which needs to be opened
     * @param row       the row which needs to be split in parts
     * @return          the parts
     */

    private static String[] readFile(String fileName, int row){

        FileHandle file = Gdx.files.internal("saves/" + fileName);
        String text = file.readString();
        if(rows.length == 0)
            rows = text.split("l");

        return rows[row].split(" ");
    }

    /**
     * Returns the row count of the save file
     *
     * @param fileName  the file's name which needs to be opened
     * @return          the the row count of the file as int
     */

    static int getSize(String fileName){

        FileHandle file = Gdx.files.internal("saves/" + fileName);
        String text = file.readString();
        String[] parts = text.split("l");

        return parts.length;
    }

    /**
     * Resets the rows variable
     */
    static void reset(){

        rows = new String[]{};
    }

}
