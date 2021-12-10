package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.*;

import static com.github.nithinmuthukumar.Globals.*;
import static com.github.nithinmuthukumar.Globals.GROUNDLEVEL;

public class SLaserEnemy extends IteratingSystem {
    private static int VULNERABLE_TIME = 5;
    public SLaserEnemy(int priority) {
        super(Family.all(CEnemy.class).exclude(CRemoval.class).get(),priority);
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


        if(state.getAction()==Action.SHOOT){
            if(animation.isAnimationFinished(state.getAction())){
                state.setAction(Action.VULNERABLE);
                animation.aniTime = 0;
                enemy.vulnerableTimer = 0;

                Entity laser = engine.createEntity();

                Body laserBody = EntityFactory.bodyBuilder("KinematicBody",body.body.getPosition().x,body.body.getPosition().y-15f/PPM);
                EntityFactory.createRectFixture(0,0,17.5f/PPM,10f/PPM,true,laser,laserBody,1,1);
                laser.add(new CWeapon(true))
                        .add(new CTransform(0,0,20,35,20))
                        .add(new CRenderable(new Texture("laser.png")))
                        .add(new CBody(laserBody,0))
                        .add(new CDecay(10f));
                engine.addEntity(laser);
                laserBody.setLinearVelocity(Math.signum(enemy.distToPlayer)*1.2f,body.body.getLinearVelocity().y);

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
        if (Math.abs(distToTarget) > 4f) {
            state.setAction(Action.RUN);
            body.body.setLinearVelocity(body.speed * Math.signum(distToTarget), body.body.getLinearVelocity().y);

        } else {
            state.setAction(Action.SHOOT);
            enemy.distToPlayer = distToTarget;
        }


    }
}
