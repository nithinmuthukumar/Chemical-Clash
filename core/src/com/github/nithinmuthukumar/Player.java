package com.github.nithinmuthukumar;



import com.badlogic.ashley.core.Entity;

//holds player info of the player on the conquestClient side of each instance
public class Player {


    private float score;
    private Entity entity;

    public Player(Entity entity) {
        score = 0;
        this.entity = entity;

    }

    public Entity getEntity() {
        return entity;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }


}