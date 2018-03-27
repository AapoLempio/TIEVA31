package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.codeandweb.physicseditor.PhysicsShapeCache;

/**
 * BodyFactory class creates bodies on request.
 * <p>
 * Project work for TIEVA31 Principles of Programming Graphical User Interfaces 2016 - 2017 course.
 *
 * @author Aapo Lempiö (al422806@student.uta.fi)
 * Created by Aapo on 9.8.2017.
 */

class BodyFactory {

    private static BodyFactory thisInstance;
    /**
     * The Box2D World
     */
    private World world;
    /**
     * The body info from the physics.xml file
     */
    private PhysicsShapeCache physicsBodies = new PhysicsShapeCache("physics.xml");

    /**
     * A body material
     */
    static final int LINE = 3;

    private static final float SCALE = 0.005f;

    /**
     *
     * @param world the Box2D World
     */
    private BodyFactory(World world){
        this.world = world;
    }

    /**
     * Gets the BodyFactory
     *
     * @param world the Box2D World
     * @return      the instance of BodyFactory
     */

    static BodyFactory getInstance(World world){
        if(thisInstance == null){
            thisInstance = new BodyFactory(world);
        }
        return thisInstance;
    }

    /**
     * Takes in a material and a shape and creates the corresponding fixture definiton for it.
     *
     * @param shape     the shape
     * @return          the fixture definition
     */

    private static FixtureDef makeFixture(Shape shape){
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.01f;
        fixtureDef.restitution = 0.01f;
        return fixtureDef;
    }

    /**
     * Creates a body using the information from the physicsBodies variable.
     *
     * @param name  the name of the body we want to create
     * @param x     the x-coordinate for the body
     * @param y     the y-coordinate for the body
     * @param rotation  the rotation for the body
     * @return      the created body
     */

    Body createBody(String name, float x, float y, float rotation){
        Body body = physicsBodies.createBody(name, world, SCALE, SCALE);
        body.setTransform(x, y, rotation);

        return body;
    }

    /**
     *
     * @param position  the coordinates for the body as a Vector3 variable
     * @param width     the width for the body
     * @param bodyType  body type for the body
     * @param rotation  the rotation for the body
     * @return          the line's body
     */

    Body createLineBody(Vector3 position, float width, BodyType bodyType, float rotation){
        BodyDef boxBodyDef = new BodyDef();
        boxBodyDef.type = bodyType;
        boxBodyDef.position.x = position.x;
        boxBodyDef.position.y = position.y;

        Body boxBody = world.createBody(boxBodyDef);
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, 0.1f, new Vector2(), rotation);
        boxBody.createFixture(makeFixture(poly));
        poly.dispose();

        return boxBody;
    }

    /**
     * Replaces the line body's fixture with a new fixture
     *
     * @param body      the body which needs a new fixture
     * @param mousepos  the mouses position
     * @param opos      the position where the mouse was when the user started to draw
     * @param rotation  the rotation for the new fixture
     * @param width     the width for the new fixture
     * @return          the body with a new fixture
     */
    Body replaceLineFixture(Body body, Vector2 mousepos, Vector2 opos, float rotation, float width){
        body.destroyFixture(body.getFixtureList().first());
        PolygonShape poly = new PolygonShape();
        poly.setAsBox(width, 0.1f, new Vector2(), rotation);

        body.createFixture(makeFixture(poly));
        poly.dispose();
        body.setTransform((opos.x + mousepos.x)/2, (opos.y + mousepos.y)/2, 0);
        return body;
    }

}
