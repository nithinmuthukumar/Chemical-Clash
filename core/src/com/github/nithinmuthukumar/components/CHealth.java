package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;

public class CHealth implements Component {
    public int life;
    public CHealth(int life){
        this.life = life;
    }
}
