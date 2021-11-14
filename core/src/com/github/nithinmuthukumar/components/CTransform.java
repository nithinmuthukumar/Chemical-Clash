package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CTransform implements Component {
    public float width, height;
    public int z;
    public float rotation;
    public Vector2 pos;


    public CTransform(float x, float y, int z, float width, float height) {
        pos = new Vector2(x, y);
        this.width = width;
        this.height = height;
        this.z = z;

    }

    //the position is the center of the sprite so when drawing the position has to be shifted
    public float getRenderX() {
        return pos.x - width / 2;
    }

    public float getRenderY() {
        return pos.y - height / 2;

    }


}