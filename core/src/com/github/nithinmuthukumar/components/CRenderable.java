package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class CRenderable implements Component{
    public TextureRegion region;
    //origin for rotation which is always set to the center of the region
    private float originX,originY = 0;
    public CRenderable(Texture texture){
        this.region = new TextureRegion(texture);
        originX = region.getRegionWidth()/2f;
        originY = region.getRegionHeight()/2f;
    }

    public CRenderable() {

    }

    public float getOriginX() {
        return originX;
    }

    public float getOriginY() {
        return originY;
    }
}
