package com.github.nithinmuthukumar.components;

import com.badlogic.gdx.Gdx;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;

import java.util.HashMap;

public class CAnimation implements Component {
    public float aniTime = 0;

    private final ObjectMap<Action, Animation<TextureRegion>> animations;



    public CAnimation(String aniPath, HashMap<Action, float[]> aniData ){
        animations = new ObjectMap<Action, Animation<TextureRegion>>();



        for(Action action:aniData.keySet()){
            Gdx.app.log("aniData", String.valueOf((int)aniData.get(action)[0]));
            TextureRegion[] frames= new TextureRegion[(int)aniData.get(action)[0]];
            for(int i = 1;i<= aniData.get(action)[0];i++){
                TextureRegion t = new TextureRegion(new Texture(aniPath+action+"/"+i+".png"));
                frames[i-1]=t;
                Gdx.app.log("animation",aniPath+action+"/"+i+".png");
                Gdx.app.log("texture",String.valueOf(t.getRegionHeight()));

            }

            animations.put(action,new Animation<TextureRegion>(aniData.get(action)[1],frames));
            Gdx.app.log("action",action.name());
            Gdx.app.log("frames length", String.valueOf(frames.length));


        }


    }
    public Animation<TextureRegion> get(Action action) {
        return animations.get(action);

    }

    //returns if the current time is greater than the time of the animation
    public boolean isAnimationFinished(Action action) {
        return animations.get(action).isAnimationFinished(aniTime);

    }
}
