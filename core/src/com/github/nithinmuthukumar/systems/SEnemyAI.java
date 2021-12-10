package com.github.nithinmuthukumar.systems;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.Globals;
import com.github.nithinmuthukumar.Rumble;
import com.github.nithinmuthukumar.components.*;



import static com.github.nithinmuthukumar.Globals.*;

public class SEnemyAI extends IteratingSystem {
    private static final float VULNERABLE_TIME = 7;
    private int slamCount = 0;
    public SEnemyAI(int priority) {
        super(Family.all(CEnemy.class).exclude(CRemoval.class).get(),priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CState state = cmState.get(entity);
        CEnemy enemy = cmEnemy.get(entity);
        CBody body = cmBody.get(entity);
        CLight light = cmLight.get(entity);
        CAnimation animation = cmAnimation.get(entity);
        CBody playerBody = cmBody.get(player.getEntity());
        float distToTarget;
        if(light.light.isActive()){
            distToTarget = playerBody.body.getPosition().x-body.body.getPosition().x;


        }else{

            distToTarget = cmBody.get(enemy.lightOrb).body.getPosition().x-body.body.getPosition().x;

        }

        if(state.getAction()==Action.VULNERABLE){

            if(enemy.vulnerableTimer<VULNERABLE_TIME){
                enemy.vulnerableTimer+=deltaTime;
                return;
            }else {
                //light.light.setActive(true);
                state.setAction(Action.IDLE);
            }



        }

        if(state.getAction()==Action.SLAM){
            if(animation.isAnimationFinished(state.getAction())){
                Rumble.rumble(1.2f,1f);
                Entity slam = engine.createEntity();
                Body slamBody = EntityFactory.bodyBuilder("StaticBody",body.body.getPosition().x,body.body.getPosition().y);
                EntityFactory.createRectFixture(0,-30f/PPM,30f/PPM,30f/PPM,true,slam,slamBody,1,1);
                slam.add(new CWeapon(true))
                        .add(new CBody(slamBody,0))
                        .add(new CDecay(0.2f));
                engine.addEntity(slam);
                slamCount+=1;
                if(slamCount%2!=0){
                    state.setAction(Action.IDLE);
                    return;
                }
                Rumble.rumble(1,1f);
                state.setAction(Action.VULNERABLE);
                enemy.vulnerableTimer=0;
                light.light.setActive(false);
                Entity ball = engine.createEntity();
                float x = body.body.getPosition().x+(state.direction==Direction.RIGHT?0.7f:-0.7f);
                Body ballBody = EntityFactory.bodyBuilder("DynamicBody", x,body.body.getPosition().y);
                EntityFactory.createCircleFixture(0,0,10/PPM,false,ball,ballBody,1,1f);
                ball.add(new CRenderable(new Texture("donut.png")))
                        .add(new CBody(ballBody,0))
                        .add(new CTransform(0,0,2,20,20))
                        .add(new CLight(new PointLight(rayHandler,100, Color.LIGHT_GRAY,1,0,0),ballBody,0,0));
                engine.addEntity(ball);
                enemy.lightOrb = ball;
                ballBody.applyForceToCenter(x*2.4f,0,true);




            }
            else{
                return;
            }
        }

        if(state.getAction()==Action.CROUCH){
            if(animation.isAnimationFinished(state.getAction())){
                state.setAction(Action.SLAM);
                animation.aniTime=0;
                body.body.applyForceToCenter(enemy.distToPlayer*16,100,true);

            }else{
                return;
            }
        }

        if(distToTarget>0){
            state.direction = Direction.RIGHT;
        }else{
            state.direction = Direction.LEFT;
        }
        boolean onGround = body.body.getPosition().y<GROUNDLEVEL;
        Gdx.app.log("onGround", String.valueOf(onGround));
        Gdx.app.log("State",state.toString());


        if(onGround&&state.getAction()!=Action.SLAM&& state.getAction()!=Action.VULNERABLE){
            if(!light.light.isActive()){
                state.setAction(Action.RUN);
                body.body.setLinearVelocity(body.speed*Math.signum(distToTarget),body.body.getLinearVelocity().y);
            }
            else if(Math.abs(distToTarget)>1.5f){

                //Move towards player
                state.setAction(Action.RUN);
                body.body.setLinearVelocity(body.speed*Math.signum(distToTarget),body.body.getLinearVelocity().y);
            }else{
                state.setAction(Action.CROUCH);
                enemy.distToPlayer = distToTarget;
            }

        }


    }
}
