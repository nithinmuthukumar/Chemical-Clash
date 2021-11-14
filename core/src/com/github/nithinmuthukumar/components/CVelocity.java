package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;

public class CVelocity extends Vector2 implements Component {
    public float magnitude;
    public CVelocity(float magnitude){
        this.magnitude = magnitude;
        set(0,0);

    }

    public CVelocity() {
    }
}
