package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.components.CHealth;
import com.github.nithinmuthukumar.components.CRemoval;
import com.github.nithinmuthukumar.screens.GameOver;

import static com.github.nithinmuthukumar.Globals.*;

public class SDeath extends IteratingSystem {
    public SDeath(int priority){
        super(Family.all(CHealth.class).exclude(CRemoval.class).get(),priority);
    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CHealth health = cmHealth.get(entity);
        if(health.life<=0){
            if(cmEnemy.has(entity)){
                cmState.get(entity).setAction(Action.KNOCKBACK);
                entity.add(new CRemoval(1));



            }else{
//                engine.removeAllEntities();
//                engine.removeAllSystems();
                game.setScreen(new GameOver());

            }

        }

    }
}
