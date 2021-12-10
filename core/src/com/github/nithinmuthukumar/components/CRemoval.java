package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;

public class CRemoval implements Component {
    public float countDown;
    public CRemoval(float countDown){
        this.countDown = countDown;
    }
}
