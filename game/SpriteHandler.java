package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.List;

/**
 * Sprite Handler class for drawing sprites.
 *
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 30.8.2017.
 */

public class SpriteHandler {
    /**
     * A hashmap to store the sprites
     */
    private final HashMap<String, Sprite> sprites = new HashMap<String, Sprite>();
    private TextureAtlas textureAtlas = new TextureAtlas("sprites.txt");

    /**
     * Draws a sprite using the given variables.
     *
     * @param name  the name of the sprite in the sprites hashmap to draw
     * @param x     the x-coordinate for the sprite
     * @param y     the y-coordinate for the sprite
     * @param degrees   the rotation for the sprite
     * @param sb    the sprite batch for the sprite
     */
    public void drawSprite(String name, float x, float y, float degrees, SpriteBatch sb){
        Sprite sprite = sprites.get(name);
        sprite.setPosition(x, y);
        sprite.setRotation(degrees);
        sprite.draw(sb);
    }

    /**
     * Creates the logo on the menuscreen.
     *
     * @return the logo
     */
    public Sprite createLogo(){
        return textureAtlas.createSprite("ViivaRaidaaja");
    }

    /**
     * Draws the spritess for the lines using the given variables.
     *
     * @param bodies        the list of the bodies to be drawn
     * @param widthList     the list of the bodies's widths
     * @param rotationList  the list of the bodies's rotations
     * @param sb            the sprite batch for the sprite
     * @param r             the red color value for the sprite
     * @param g             the green color value for the sprite
     * @param b             the blue color value for the sprite
     */
    public void drawBodySprite(List<Body> bodies, List<Float> widthList, List<Float> rotationList, SpriteBatch sb, float r, float g, float b){
        for(int i = 0; i<bodies.size(); i++){
            Body body = bodies.get(i);
            Sprite sprite = sprites.get("white");
            sprite.setCenter(body.getPosition().x, body.getPosition().y);
            sprite.setSize(widthList.get(i) * 2f, 0.2f);
            sprite.setColor(r,g,b,1);
            sprite.setOriginCenter();
            sprite.setRotation((float) Math.toDegrees(rotationList.get(i)));
            sprite.setCenter(body.getPosition().x, body.getPosition().y);
            sprite.draw(sb);
        }
    }

    /**
     * Extracts the sprites from the textureRegion to the sprites hashmap.
     */
    public void addSprites(){
        Array<AtlasRegion> regions = textureAtlas.getRegions();

        for(AtlasRegion region : regions){
            Sprite sprite = textureAtlas.createSprite(region.name);

            float SCALE = 0.005f;
            float width = sprite.getWidth() * SCALE;
            float height = sprite.getHeight() * SCALE;

            sprite.setSize(width, height);
            sprite.setOrigin(0f, 0f);

            sprites.put(region.name, sprite);
        }
    }
}
