package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

public class CBody implements Component {
    public Body body;
    public boolean isSticky;
    public CBody(Body body){
        this.body = body;
        isSticky = false;
    }

}
