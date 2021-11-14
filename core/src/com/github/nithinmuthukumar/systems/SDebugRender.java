package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.signals.Listener;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.github.nithinmuthukumar.Globals;

import static com.github.nithinmuthukumar.Globals.mainCamera;

public class SDebugRender extends IteratingSystem {

    private Box2DDebugRenderer debugRenderer=new Box2DDebugRenderer();
    //holds all the rectangles that need to be drawn
    private boolean debug;
    //this is the Matrix of the screen which allows for objects to be drawn in terms of the screen
    private Matrix4 screenView;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();
    private Array<Color> colors;

    public SDebugRender(int priority) {

        super(Family.all().get(), priority);
        colors=new Array<Color>();
        //colors used to draw the tiled map collision
        colors.add(new Color(0,0,0,0));
        colors.add(Color.BLACK.add(0,0,0,-0.5f));
        colors.add(Color.YELLOW.add(0,0,0,-0.5f));
        colors.add(Color.YELLOW.add(0,0,0,-0.3f));
        colors.add(Color.BLUE.add(0,0,0,-0.5f));

        debug = true;
        screenView = shapeRenderer.getProjectionMatrix().cpy();
        //this flushes the batch to draw filled then line after one another without running shapeRenderer again
        shapeRenderer.setAutoShapeType(true);

    }


    @Override
    public void update(float deltaTime) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(mainCamera.combined);
        if (debug)
            debugRenderer.render(Globals.world, mainCamera.combined);

//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        //draws the tiles so that they can be seen
//        shapeRenderer.setColor(1, 1, 1, 1);
//
//        //the requests are always in terms of the screen
//        shapeRenderer.setProjectionMatrix(screenView);
//        shapeRenderer.set(ShapeRenderer.ShapeType.Line);
//
//
//        shapeRenderer.end();



    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {

    }
}
