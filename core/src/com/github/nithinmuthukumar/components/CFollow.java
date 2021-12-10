package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Vector2;

public class CFollow implements Component {
    public Vector2 offset;
    public Entity transform;
    public String nextImgPath;
    public boolean isCore;
    public CFollow(Entity transform, float offsetX, float offsetY, String nextImgPath){
        offset = new Vector2(offsetX,offsetY);
        this.transform = transform;
        this.nextImgPath = nextImgPath;
        isCore = false;


    }
    public CFollow(Entity transform, float offsetX, float offsetY,boolean isCore){
        offset = new Vector2(offsetX,offsetY);
        this.transform = transform;
        this.isCore = isCore;
    }
}
