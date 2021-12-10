package com.github.nithinmuthukumar.systems;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.CBody;
import com.github.nithinmuthukumar.components.CLight;
import com.github.nithinmuthukumar.components.CRemoval;
import com.github.nithinmuthukumar.components.CState;
import com.github.nithinmuthukumar.screens.Play;

import static com.github.nithinmuthukumar.Globals.*;

public class SCollision extends IteratingSystem {
    public SCollision(int priority){
        super(Family.all(CBody.class).exclude(CRemoval.class).get(),priority);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CBody body = cmBody.get(entity);
        while(body.collidedEntities.notEmpty()){
            Entity e = body.collidedEntities.removeFirst();
            if(!cmRemoval.has(e)){
                if(cmEnemy.has(entity)){
                    if(cmEnemy.get(entity).lightOrb==e){
                        cmLight.get(entity).light.setActive(true);
                        e.add(new CRemoval(0));
                    }

                }

            }
            if(cmWeapon.has(entity)){
                if(cmPlayer.has(e)){
                    entity.add(new CRemoval(0));
                    cmAnimation.get(e).aniTime = 0;
                    cmState.get(e).setAction(Action.KNOCKBACK);
                    cmHealth.get(e).life-=1;
                    Gdx.app.log("Collision with player",String.valueOf(cmHealth.get(e).life));

                }else if(cmEnemy.has(e)&&(Play.level>2||!cmLight.get(e).light.isActive())){
                    CState enemyState = cmState.get(e);
                    boolean inFront = false;
                    if(enemyState.direction== Direction.RIGHT){
                        inFront = cmBody.get(e).body.getPosition().x<body.body.getPosition().x;
                    }else if(enemyState.direction==Direction.LEFT){
                        inFront = cmBody.get(e).body.getPosition().x>body.body.getPosition().x;
                    }

                    if(Play.level==2){
                        if(!inFront) return;

                    }else if(Play.level==3){
                        if(inFront) return;
                        if(cmHealth.get(e).life==1){
                            e.add(new CLight(new PointLight(rayHandler,150, Color.WHITE,2,0,0),cmBody.get(e).body,0,-30f/PPM));
                        }
                    }else if(Play.level==4){
                        if(cmState.get(player.getEntity()).getAction()!=Action.SLIDE){
                            return;
                        }
                    }
                    cmHealth.get(e).life-=1;

                    Gdx.app.log("Collision with enemy",String.valueOf(cmHealth.get(e).life));
                }

            }


        }



    }
}
