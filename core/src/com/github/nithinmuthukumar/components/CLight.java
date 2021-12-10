package com.github.nithinmuthukumar.components;

import box2dLight.Light;
import box2dLight.PointLight;
import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.physics.box2d.Body;

import static com.github.nithinmuthukumar.Globals.PPM;

public class CLight implements Component {
    public PointLight light;
    public CLight(PointLight light, Body body,float offsetX,float offsetY){
        this.light = light;
        light.attachToBody(body,offsetX,offsetY);


        light.setIgnoreAttachedBody(true);

    }
}
