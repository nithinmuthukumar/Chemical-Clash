package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.CBody;
import com.github.nithinmuthukumar.components.CState;
import com.github.nithinmuthukumar.components.CVelocity;

import static com.github.nithinmuthukumar.Globals.*;

public class SMovement extends IteratingSystem {

    public SMovement(int priority) {
        super(Family.all(CBody.class, CVelocity.class).get(),priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CVelocity velocity = cmVelocity.get(entity);
        CBody body = cmBody.get(entity);
        if(cmState.has(entity)){
            CState state = cmState.get(entity);
            if(state.action== Action.IDLE){
                velocity.x = 0;


            }else if (state.action==Action.MOVE){
                int dir = state.direction== Direction.RIGHT?1:-1;
                velocity.x=velocity.magnitude*dir;
            }else if(state.action==Action.JUMP){

            }
        }

        body.body.setLinearVelocity(velocity.x*PPM,body.body.getLinearVelocity().y);



    }
}
