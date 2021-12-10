package com.github.nithinmuthukumar.systems;

import com.badlogic.gdx.Gdx;
import com.github.nithinmuthukumar.Enums.Action;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.CAnimation;
import com.github.nithinmuthukumar.components.CRenderable;
import com.github.nithinmuthukumar.components.CState;

import static com.github.nithinmuthukumar.Globals.*;

public class SAnimation extends IteratingSystem {

    public SAnimation(int priority) {
        super(Family.all(CAnimation.class, CState.class, CRenderable.class).get(),priority);

    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        //gets the animation and increments the animation time
        CAnimation animation = cmAnimation.get(entity);

        animation.aniTime+=deltaTime;

        CState state=cmState.get(entity);
        if(state.getAction()!=state.getPreviousAction()){
            animation.aniTime=0;
            state.setPreviousAction(state.getAction());
        }
        CRenderable renderable=cmRenderable.get(entity);
        //gets the animation and sets the region as that value



        renderable.region =animation.get(state.getAction()).getKeyFrame(animation.aniTime,true);


//        if (state.getAction() == Action.SLASH1||state.getAction()==Action.SLASH2||state.getAction()==Action.SLASH3) {
//            if (animation.isAnimationFinished(state.getAction())) {
//
//                state.setAction(Action.IDLE);
//                return;
//            }
//        }





    }
}
