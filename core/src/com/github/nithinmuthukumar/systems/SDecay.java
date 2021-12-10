package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.github.nithinmuthukumar.components.CDecay;
import com.github.nithinmuthukumar.components.CRemoval;

import static com.github.nithinmuthukumar.Globals.cmDecay;

public class SDecay extends IteratingSystem {
    public SDecay(int priority){
        super(Family.all(CDecay.class).get(),priority);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CDecay decay = cmDecay.get(entity);
        if(decay.countDown<=0){
            entity.add(new CRemoval(0));
        }
        decay.countDown-=deltaTime;

    }
}
