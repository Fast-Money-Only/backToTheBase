package com.mygdx.game;
// e4
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.mygdx.game.Constants.PPM;

public class BodyHelperService {

    public static Body createBodyFromShape(PolygonShape shape, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(0, 0);
        Body body = world.createBody(bodyDef);

        //shape.setAsBox(64 / 2 / PPM, 32 / 2 / PPM); //Collisoncheck med grænser


        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0; // prevent sticking to walls
        fixtureDef.density = 1000;
        body.createFixture(fixtureDef);

        return body;
    }


    /*public static Body createBody(float x, float y, float width, float height, boolean isStatic, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = isStatic ? BodyDef.BodyType.StaticBody : BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x / PPM, y / PPM);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0; // prevent sticking to walls
        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }*/
}
