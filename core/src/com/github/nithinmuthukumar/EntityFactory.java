package com.github.nithinmuthukumar;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static com.github.nithinmuthukumar.Globals.world;

public class EntityFactory {

    public static Fixture createRectFixture(float x, float y, float hx, float hy, boolean isSensor, Entity e, Body body, float density, float friction) {
        PolygonShape rect = new PolygonShape();
        rect.setAsBox(hx, hy, new Vector2(x, y), 0);
        return createFixture(rect, isSensor, e, body, density, friction);
    }
    public static Fixture createCircleFixture(float x, float y, float radius, boolean isSensor, Entity e, Body body, float density, float friction) {
        CircleShape circle = new CircleShape();
        circle.setRadius(radius);
        circle.setPosition(new Vector2(x, y));
        return createFixture(circle, isSensor, e, body, density, friction);
    }
    public static Fixture createFixture(Shape shape, boolean isSensor, Entity e, Body body, float density, float friction) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = isSensor;
        fixtureDef.density = density;
        fixtureDef.friction = friction;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(e);
        return fixture;
    }
    //creates the body from a type and position
    public static Body bodyBuilder(String bodyType, float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.valueOf(bodyType);
        bodyDef.position.set(x, y);
        return world.createBody(bodyDef);


    }


}
