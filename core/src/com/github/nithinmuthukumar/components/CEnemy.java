package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;

public class CEnemy implements Component {
    public float distToPlayer;
    public float vulnerableTimer;
    public Entity lightOrb;
}
