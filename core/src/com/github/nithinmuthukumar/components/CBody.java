package com.github.nithinmuthukumar.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Queue;

import java.util.Deque;

public class CBody implements Component {
    public Body body;
    public float speed;
    public boolean isSticky;
    public Queue<Entity> collidedEntities;

    public CBody(Body body,float speed){
        this.body = body;
        this.speed = speed;
        isSticky = false;
        collidedEntities = new Queue<Entity>();
    }



}
