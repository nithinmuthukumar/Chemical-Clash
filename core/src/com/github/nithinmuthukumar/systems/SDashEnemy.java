package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.Rumble;
import com.github.nithinmuthukumar.components.*;

import javax.swing.text.html.parser.DTD;

import static com.github.nithinmuthukumar.Globals.*;

public class SDashEnemy extends IteratingSystem {
    private static final float VULNERABLE_TIME = 4;

    public SDashEnemy(int priority) {
        super(Family.all(CEnemy.class).exclude(CRemoval.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CState state = cmState.get(entity);
        CEnemy enemy = cmEnemy.get(entity);
        CBody body = cmBody.get(entity);
        CAnimation animation = cmAnimation.get(entity);
        CBody playerBody = cmBody.get(player.getEntity());
        Gdx.app.log("state",state.toString());
        float distToTarget = playerBody.body.getPosition().x - body.body.getPosition().x;

        if(state.getAction()== Action.VULNERABLE){
            Gdx.app.log("vulnerable", String.valueOf(enemy.vulnerableTimer));
            if(enemy.vulnerableTimer<VULNERABLE_TIME){

                enemy.vulnerableTimer+=deltaTime;
                return;
            }else{
                //enemy.vulnerableTimer=0;
                state.setAction(Action.IDLE);
            }
        }

        if(state.getAction()==Action.DASH) {
            Gdx.app.log("anitime", String.valueOf(animation.aniTime));
            if (animation.isAnimationFinished(state.getAction())) {
                Gdx.app.log("animationdone","hh");
                state.setAction(Action.VULNERABLE);
                animation.aniTime = 0;
                enemy.vulnerableTimer = 0;
                body.body.setLinearVelocity(0,body.body.getLinearVelocity().y);


            }
            return;
        }
        if(state.getAction()==Action.CROUCH){
            if(animation.isAnimationFinished(state.getAction())){
                state.setAction(Action.DASH);
                animation.aniTime = 0;
                body.body.setLinearVelocity(Math.signum(enemy.distToPlayer)*4.5f,body.body.getLinearVelocity().y);

                Entity dash = engine.createEntity();
                Body dashBody = EntityFactory.bodyBuilder("KinematicBody",body.body.getPosition().x,body.body.getPosition().y);
                EntityFactory.createRectFixture(0,-30f/PPM,15f/PPM,30f/PPM,true,dash,dashBody,1,1);
                dash.add(new CWeapon(true))
                        .add(new CBody(dashBody,0))
                        .add(new CDecay(0.3f));
                engine.addEntity(dash);
                dashBody.setLinearVelocity(body.body.getLinearVelocity());

                return;
            }else{
                return;
            }
        }


        if (distToTarget > 0) {
            state.direction = Direction.RIGHT;
        } else
            state.direction = Direction.LEFT;


        boolean onGround = body.body.getPosition().y < GROUNDLEVEL;
        if (Math.abs(distToTarget) > 1.5f) {
            state.setAction(Action.RUN);
            body.body.setLinearVelocity(body.speed * Math.signum(distToTarget), body.body.getLinearVelocity().y);

        } else {
            state.setAction(Action.CROUCH);
            enemy.distToPlayer = distToTarget;
        }
    }
}





