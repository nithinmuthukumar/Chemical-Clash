package com.github.nithinmuthukumar.systems;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.components.*;
import com.github.nithinmuthukumar.screens.Play;

import static com.github.nithinmuthukumar.Globals.*;

public class SFollow extends IteratingSystem {
    private boolean switchImage=false;
    public SFollow(int priority){
        super(Family.all(CFollow.class).get(),priority);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CTransform transform = cmTransform.get(entity);
        CFollow follow = cmFollow.get(entity);

        if(follow.isCore){
            transform.pos.x = cmTransform.get(follow.transform).pos.x+(cmState.get(follow.transform).direction==Direction.LEFT?-follow.offset.x:follow.offset.x);
            transform.pos.y = cmTransform.get(follow.transform).pos.y+follow.offset.y;
        }else {
            transform.pos = follow.offset.cpy().add(cmTransform.get(follow.transform).pos);
        }
        if(cmRemoval.has(follow.transform)){
            switchImage = true;
            if (follow.isCore) {
                entity.remove(CFollow.class);
                Body ballBody = EntityFactory.bodyBuilder("DynamicBody", transform.pos.x/PPM,transform.pos.y/PPM);
                EntityFactory.createCircleFixture(0,0,5/PPM,false,entity,ballBody,1,1f);
                entity.add(new CBody(ballBody,0))
                        .add(new CLight(new PointLight(rayHandler,100, Color.RED,1,0,0),ballBody,0,0));


            }


        }
        if(switchImage&&follow.nextImgPath!=null){
            cmRenderable.get(entity).region = new TextureRegion(new Texture(follow.nextImgPath));

        }





    }
}
