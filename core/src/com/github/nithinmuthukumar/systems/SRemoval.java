package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.nithinmuthukumar.components.CRemoval;

import static com.github.nithinmuthukumar.Globals.cmRemoval;

public class SRemoval extends IteratingSystem {
    public SRemoval(int priority){
        super(Family.all(CRemoval.class).get(),priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CRemoval removal = cmRemoval.get(entity);
        if(removal.countDown<=0){
            getEngine().removeEntity(entity);
        }
        removal.countDown-=deltaTime;

    }
}
