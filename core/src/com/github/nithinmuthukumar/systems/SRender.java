package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.Rumble;
import com.github.nithinmuthukumar.components.CRenderable;
import com.github.nithinmuthukumar.components.CState;
import com.github.nithinmuthukumar.components.CTransform;

import static com.github.nithinmuthukumar.Globals.*;

public class SRender extends SortedIteratingSystem {
    public SRender(int priority){
        super(Family.all(CRenderable.class, CTransform.class).get(), zyComparator,priority);
    }
    @Override
    public void update(float deltaTime){

        if (Rumble.getRumbleTimeLeft() > 0){

            Rumble.tick(deltaTime);
            mainCamera.translate(Rumble.getPos());
        }
        mainCamera.update();
        batch.setProjectionMatrix(mainCamera.combined);
        rayHandler.setCombinedMatrix(mainCamera.combined.cpy().scl(PPM));
        forceSort();
        Gdx.gl.glClearColor(0.09f,0.05f,0.2f,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.update(deltaTime);
        batch.end();

        rayHandler.render();

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CTransform transform = cmTransform.get(entity);
        CRenderable renderable = cmRenderable.get(entity);

        float x =transform.getRenderX();
        float width = transform.width;
        if(cmState.has(entity)){
            CState state = cmState.get(entity);


            if(state.direction!= Direction.NONE){
                if(state.direction==Direction.LEFT){
                    x +=transform.width;
                    width*=-1;

                }


            }
        }
        if (cmEnemy.has(entity)) {
            batch.setColor(new Color(1,0.6f,0.6f,1));
        } else batch.setColor(1, 1, 1, 1);
        batch.draw(renderable.region,x,transform.getRenderY(),renderable.getOriginX(),
                renderable.getOriginY(),width,transform.height,
                1,1,transform.rotation);

    }
}
