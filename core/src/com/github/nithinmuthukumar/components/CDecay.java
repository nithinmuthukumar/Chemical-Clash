package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;

public class CDecay implements Component {
    public float countDown;
    public CDecay(float countDown){
        this.countDown = countDown;
    }
}
