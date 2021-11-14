package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IntervalIteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.github.nithinmuthukumar.Globals;
import com.github.nithinmuthukumar.components.CBody;
import com.github.nithinmuthukumar.components.CTransform;
import com.github.nithinmuthukumar.components.CVelocity;

import static com.github.nithinmuthukumar.Globals.*;

public class SPhysics extends IntervalIteratingSystem {

    public SPhysics(int priority) {
        super(Family.all(CBody.class, CTransform.class).get(), 1f/60f,priority);
    }

    @Override
    protected void updateInterval() {
        world.step(1f/60f,6,2);
        super.updateInterval();
    }

    @Override
    protected void processEntity(Entity entity) {
        CTransform transform = cmTransform.get(entity);
        CBody body = cmBody.get(entity);

        transform.pos.x = body.body.getPosition().x;
        transform.pos.y = body.body.getPosition().y;

        //body.body.setTransform(body.body.getWorldCenter(), MathUtils.degreesToRadians*transform.rotation);

    }


}
