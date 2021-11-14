package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.github.nithinmuthukumar.components.CRenderable;
import com.github.nithinmuthukumar.components.CTransform;

import static com.github.nithinmuthukumar.Globals.*;

public class SRender extends SortedIteratingSystem {
    public SRender(int priority){
        super(Family.all(CRenderable.class, CTransform.class).get(), zyComparator,priority);
    }
    @Override
    public void update(float deltaTime){
        batch.setProjectionMatrix(mainCamera.combined);
        forceSort();
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        super.update(deltaTime);
        batch.end();

    }
    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CTransform transform = cmTransform.get(entity);
        CRenderable renderable = cmRenderable.get(entity);
        batch.draw(renderable.region,transform.getRenderX(),transform.getRenderY(),renderable.getOriginX(),
                renderable.getOriginY(),transform.width,transform.height,
                1,1,transform.rotation);

    }
}
