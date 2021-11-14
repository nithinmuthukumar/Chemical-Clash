package com.github.nithinmuthukumar.components;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.github.nithinmuthukumar.Globals;

public class CAnimation implements Component {
    public float aniTime = 0;

    private ObjectMap<Action,ObjectMap<Direction,Animation<TextureRegion>>> animations;



    public CAnimation(String aniPath, float frameSpeed, ObjectIntMap<Action> aniData, Direction... directions){
        animations = new ObjectMap<Action, ObjectMap<Direction, Animation<TextureRegion>>>();



        for(Action action:aniData.keys()){

            for(Direction direction: directions){
                Gdx.app.log("anim",action.name()+direction.name());

                Texture t = new Texture(aniPath+action.name()+"/"+direction.name()+".png");
                TextureRegion[] frames = TextureRegion.split(t,t.getWidth()/aniData.get(action,0),t.getHeight())[0];
                put(action,direction,new Animation<TextureRegion>(frameSpeed,frames));
            }
        }


    }
    public Animation<TextureRegion> get(Action action, Direction direction) {
        return animations.get(action).get(direction);

    }

    private void put(Action action, Direction direction, Animation<TextureRegion> sprites) {
        if(!animations.containsKey(action)){
            animations.put(action,new ObjectMap<Direction, Animation<TextureRegion>>());
        }
        animations.get(action).put(direction,sprites);
    }
    //returns if the current time is greater than the time of the animation
    public boolean isAnimationFinished(Action action, Direction direction) {
        return animations.get(action).get(direction).isAnimationFinished(aniTime);

    }
}
