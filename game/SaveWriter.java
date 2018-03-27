package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.physics.box2d.Body;

import java.util.List;
import java.util.regex.Pattern;

/**
 * SaveWriter class writes the save file for the bodies' information.
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 30.8.2017.
 */

class SaveWriter {

    /**
     * Writes the save file
     *
     * @param bodies        the list of the bodies to be saved
     * @param rotationList  the list of the bodies' rotations
     * @param widthList     the list of the bodies' widths
     * @param fileName      the file name for the new file
     */

    static void write(List<Body> bodies, List<Float> rotationList, List<Float> widthList, String fileName){
        String bodyData = "";
        /*
         * Puts all the bodies' information in one string
         */
        for(int i = 0; i < bodies.size(); i++){
            Body body = bodies.get(i);
            float rotation = rotationList.get(i);
            float width = widthList.get(i);
            bodyData += "l" + body.getPosition().x + " " + body.getPosition().y + " " + rotation + " " + width;
        }
        fileName = fileName.replaceAll(" ","_");
        fileName = fileName.replaceAll(Pattern.quote("."),"");
        fileName = fileName.replaceAll(Pattern.quote(":"),"-");
        FileHandle file = Gdx.files.local("saves/"+ fileName + ".txt");
        file.writeString(bodyData, false);
    }
}
