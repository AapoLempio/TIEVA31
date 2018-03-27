package com.mygdx.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.controller.KeyboardController;

import java.util.ArrayList;
import java.util.List;


/**
 * B2dModel class controls the bodies in the world.
 *
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 9.8.2017.
 */


public class B2dModel {

    private OrthographicCamera camera;
    private KeyboardController controller;
    /**
     * The Box2d world
     */
    public World world;
    /**
     * Rider's body
     */
    public Body player;

    /*
     * The bodies and their information. Its difficult to get the width and rotation information
     * from the bodies so I made two helper lists: widthList and rotationList.
     */
    /**
     * List of bodies in the world
     */
    public List<Body> bodies = new ArrayList<Body>();
    /**
     * List of bodies' widths
     */
    public List<Float> widthList= new ArrayList<Float>();
    /**
     * List of bodies' rotation info
     */
    public List<Float> rotationList= new ArrayList<Float>();

    /*
     * Like the above lists, but they're for the undone bodies
     */
    /**
     * List of bodies, undone from the world
     */
    public List<Body> undoneBodies = new ArrayList<Body>();
    /**
     * List of undone bodies' widths
     */
    public List<Float> undoneWidthList= new ArrayList<Float>();
    /**
     * List of undone bodies' rotation info
     */
    public List<Float> undoneRotList= new ArrayList<Float>();

    /**
     * Variable for storing the unprojected (coordinates adjusted to the camera) mouse position
     */
    public Vector3 position = new Vector3(0,0, 0);
    /**
     * Logic step uses this to determine, if it should create a new body.  If bodyExists, then it doesn't create a new one.
     */
    private boolean bodyExists = false;
    /**
     * The mouse's position when the user starts to draw the line.
     */
    private Vector2 opos = new Vector2(0,0);

    /**
     * Initializes the world and bodyFactory and creates the rider.
     *
     * @param cont  the calling class' keyboard controller
     * @param cam    the calling class' camera
     */
    public B2dModel(KeyboardController cont, OrthographicCamera cam){
        camera = cam;
        controller = cont;
        world = new World(new Vector2(0, -10f), true);

        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        player = bodyFactory.createBody("Rider", 0, 0, 0);
    }

    /**
     * Does all the functionality regarding the bodies and steps world time
     *
     * @param delta     delta
     * @param paused    is the game paused
     */
    public void logicStep(float delta, boolean paused){
        /*
         * If wasLoaded was set true in the settings screen then, this loads the bodies from the chosen save file
         */
        if(GameLoader.wasLoaded){
            loadBodies(GameLoader.getName());
        }
        /*
         * If the game is paused, then the user is allowed to draw.
         */
        if(paused) {
            /*
             * While the game is paused, the player is set inactive
             */
            player.setActive(false);
            position = camera.unproject(new Vector3(controller.mouseLocation.x, controller.mouseLocation.y, 0));
            /*
             * If right mouse button is pressed, and the cursor intersect a body, then the body is deleted
             */
            if(controller.isMouse2Down && !controller.isMouse1Down){
                for (int i = 0; i < bodies.size(); i++) {
                    Body body = bodies.get(i);
                    if(pointIntersectsBody(body, new Vector2(position.x, position.y))){
                        world.destroyBody(body);
                        bodies.remove(body);
                        widthList.remove(i);
                        rotationList.remove(i);
                        undoneRotList.clear();
                        undoneWidthList.clear();
                        undoneBodies.clear();
                    }
                }
            }
            /*
             * If left mouse button is pressed and the cursor is dragged, the it draws a line according
             * to the mouse's coordinates
             */
            if (controller.isDragged && controller.isMouse1Down && !controller.isMouse2Down) {
                BodyFactory bodyFactory = BodyFactory.getInstance(world);
                /*
                 * If body already exists when dragging then change the body's fixtures rotation,
                 * width and cursor end coordinates
                 */
                if (bodyExists) {
                    Body body = bodies.get(bodies.size() - 1);

                    bodies.remove(bodies.size() - 1);
                    widthList.remove(widthList.size() - 1);
                    rotationList.remove(rotationList.size() - 1);

                    Vector2 mousepos = new Vector2(position.x, position.y);
                    float rotation = (float) Math.atan((mousepos.y - opos.y) / (mousepos.x - opos.x));
                    float width = ((float) Math.sqrt((mousepos.x - opos.x) * (mousepos.x - opos.x) + (mousepos.y - opos.y) * (mousepos.y - opos.y)))/2;

                    bodies.add(bodyFactory.replaceLineFixture(body, mousepos, opos, rotation, width));
                    widthList.add(width);
                    rotationList.add(rotation);
                }
                /*
                 * If the user just started dragging and there is no body yet, then create a body
                 */
                else {
                    Body body = bodyFactory.createLineBody(position, 1, BodyDef.BodyType.StaticBody, 0);
                    bodies.add(body);
                    widthList.add((float) 0);
                    rotationList.add((float) 0);
                    bodyExists = true;
                    opos = new Vector2(position.x, position.y);

                    undoneRotList.clear();
                    undoneWidthList.clear();
                    undoneBodies.clear();
                }
            }
            /*
             * If user stops dragging, then !bodyExists so that when the user starts dragging again, it draws a new body.
             */
            else if (!controller.isDragged && !controller.isMouse1Down) {
                bodyExists = false;
            }
        }
        /*
         * When the game is not paused the player is set active.
         */
        else {
            player.setActive(true);
        }
        /*
         * The Box2D world takes a time step.
         */
        world.step(delta, 3, 3);
    }

    /**
     * Loads the bodies from the given file
     *
     * @param fileName the chosen file
     */
    private void loadBodies(String fileName){
        /*
         * Reset the parser incase the it was already used during this session
         */
        SaveParser.reset();
        BodyFactory bodyFactory = BodyFactory.getInstance(world);
        int rows = SaveParser.getSize(fileName);
        /*
         * Creates bodies according to the save file
         */
        for(int i = 1; i < rows; i++) {
            float width = SaveParser.parseWidth(fileName, i);
            float rotation = SaveParser.parseRotation(fileName, i);
            Vector2 position = SaveParser.parsePosition(fileName, i);
            Body body = bodyFactory.createLineBody(new Vector3(position, 0), width, BodyDef.BodyType.StaticBody, rotation);
            bodies.add(body);
            widthList.add(width);
            rotationList.add(rotation);
        }
    }

    /**
     * Saves the bodies into a file
     *
     * @param fileName  the file name for the save
     */
    public void saveBodies(String fileName){
        SaveWriter.write(bodies, rotationList, widthList, fileName);
    }

    /**
     * Checks if the mouse's location intersects with a fixture
     *
     * @param body          the body to be checked
     * @param mouselocation the mouse's location
     * @return              did the point intersect the body
     */
    private boolean pointIntersectsBody(Body body, Vector2 mouselocation){
        return body.getFixtureList().first().testPoint(mouselocation.x, mouselocation.y);
    }

    /**
     * Clears all the line bodies from the world
     */
    public void clearWorld(){
        for(int i = 0; i<bodies.size(); i++){
            world.destroyBody(bodies.get(i));
        }
        bodies.clear();
        rotationList.clear();
        widthList.clear();
        undoneRotList.clear();
        undoneWidthList.clear();
        undoneBodies.clear();
    }

    /**
     * Undoes the last line body
     */
    public void undo(){
        if(bodies.size() > 0){
            Body body = bodies.get(bodies.size() - 1);
            float width = widthList.get(widthList.size()- 1);
            float rotation = rotationList.get(rotationList.size() - 1);

            undoneWidthList.add(width);
            widthList.remove(width);

            undoneRotList.add(rotation);
            rotationList.remove(rotation);

            undoneBodies.add(body);
            bodies.remove(body);
            world.destroyBody(body);
        }
    }

    /**
     * Redoes the last undone line body. The body is created again with the parameters from the undone- lists.
     */
    public void redo(){
        if(undoneBodies.size() > 0){
            BodyFactory bodyFactory = BodyFactory.getInstance(world);
            Body body = undoneBodies.get(undoneBodies.size() - 1);
            float width = undoneWidthList.get(undoneWidthList.size() - 1);
            float rotation = undoneRotList.get(undoneRotList.size() - 1);

            bodyFactory.createLineBody(new Vector3(body.getPosition().x, body.getPosition().y, 0), width, BodyDef.BodyType.StaticBody, rotation);

            bodies.add(body);
            widthList.add(width);
            rotationList.add(rotation);

            undoneBodies.remove(body);
            undoneWidthList.remove(width);
            undoneRotList.remove(rotation);
        }
    }

}
