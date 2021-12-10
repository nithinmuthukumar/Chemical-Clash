package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.*;

import static com.github.nithinmuthukumar.Globals.*;

public class SPlayerMovement extends IteratingSystem {

    public SPlayerMovement(int priority) {
        super(Family.all(CBody.class, CPlayer.class, CState.class).get(),priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

        CBody body = cmBody.get(entity);
        CState state = cmState.get(entity);
        CAnimation animation = cmAnimation.get(entity);
        if(state.getAction()==Action.KNOCKBACK||state.getAction()==Action.SLIDE){
            if(cmAnimation.get(entity).isAnimationFinished(state.getAction())){
                state.setAction(Action.IDLE);
            }
            else {
                return;
            }
        }


        boolean onGround = body.body.getPosition().y<GROUNDLEVEL;
        Vector2 velocity = body.body.getLinearVelocity();
        if(Gdx.input.isKeyJustPressed(Input.Keys.S)&&state.getAction()==Action.IDLE){
            if(onGround){
                state.setAction(Action.SLIDE);
                createSlash(body);
                return;
            }

        }

        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            if(onGround){
                state.setAction(Action.RUN);

                body.body.setLinearVelocity(-body.speed,velocity.y);

            }
            state.direction = Direction.LEFT;




        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            if(onGround){
                state.setAction(Action.RUN);

                body.body.setLinearVelocity(body.speed,velocity.y);

            }
            state.direction = Direction.RIGHT;


        }else if(onGround){
            if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
                if(state.getAction()==Action.IDLE||state.getAction()==Action.RUN){
                    animation.aniTime = 0;
                    state.setAction(Action.SLASH1);
                    createSlash(body);



                }
                else if(state.getAction()==Action.SLASH1&&animation.aniTime>0.4f){
                    animation.aniTime = 0;
                    state.setAction(Action.SLASH2);
                    createSlash(body);

                }
                else if(state.getAction()==Action.SLASH2&&animation.aniTime>0.4f){
                    animation.aniTime = 0;
                    state.setAction(Action.SLASH3);
                    createSlash(body);

                }





            }else if(state.getAction()!=Action.SLASH1
                    &&state.getAction()!=Action.SLASH2
                    &&state.getAction()!=Action.SLASH3
                    &&state.getAction()!=Action.IDLE&&state.getAction()!=Action.SLIDE||animation.isAnimationFinished(state.getAction())){
                state.setAction(Action.IDLE);
                body.body.setLinearVelocity(0, velocity.y);

            }
        }
        if(onGround){
            if(Gdx.input.isKeyJustPressed(Input.Keys.W)){
                state.setAction(Action.JUMP);
                body.body.applyForceToCenter(velocity.x,100,true);

            }
        }





    }
    public void createSlash(CBody body){
        Entity slash1 = engine.createEntity();
        Body slash1Body = EntityFactory.bodyBuilder("StaticBody",body.body.getPosition().x,body.body.getPosition().y);
        EntityFactory.createRectFixture(0,-30f/PPM,30f/PPM,30f/PPM,true,slash1,slash1Body,1,1);
        slash1.add(new CWeapon(false))
                .add(new CBody(slash1Body,0))
                .add(new CDecay(0.2f));
        engine.addEntity(slash1);

    }
}
