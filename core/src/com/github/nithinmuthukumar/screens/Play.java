package com.github.nithinmuthukumar.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.Player;
import com.github.nithinmuthukumar.components.*;
import com.github.nithinmuthukumar.systems.*;
// TODO jump
// TODO fix player input when touchup check other button down
// TODO PUT Ground underneath player
// TODO add javadoc comments
// Game concept topdown or platform?

import java.util.HashMap;

import static com.github.nithinmuthukumar.Globals.engine;
import static com.github.nithinmuthukumar.Globals.player;


public class Play implements Screen {

    private InputMultiplexer multiplexer = new InputMultiplexer();
    @Override
    public void show() {

        engine.addSystem(new SRender(3));
        engine.addSystem(new SAnimation(0));
        engine.addSystem(new SPhysics(2));
        engine.addSystem(new SMovement(1));
        engine.addSystem(new SDebugRender(4));
        setupGame();
        multiplexer.addProcessor(new PlayerInput());
        Gdx.input.setInputProcessor(multiplexer);




    }

    private void setupGame() {
        ObjectIntMap<Action> spiderFrames = new ObjectIntMap<Action>();
        spiderFrames.put(Action.IDLE,5);
        spiderFrames.put(Action.MOVE,6);
        spiderFrames.put(Action.JUMP,9);
        spiderFrames.put(Action.EMERGE,1);
        spiderFrames.put(Action.SHOOT,4);
        spiderFrames.put(Action.DAMAGE,3);
        spiderFrames.put(Action.DEATH,9);
        Entity spider = engine.createEntity();

        Body body = EntityFactory.bodyBuilder("DynamicBody",0,0);
        EntityFactory.createRectFixture(0,0,25,25,false,spider,body,1,1);
        spider.add(new CRenderable(new Texture("spider/EMERGE/LEFT.png")))
                .add(new CTransform(0,0,0,100,100))
                .add(new CAnimation("spider/",0.12f,spiderFrames,Direction.LEFT,Direction.RIGHT))
                .add(new CBody(body))
                .add(new CVelocity(1.2f))

                .add(new CState(Action.DEATH, Direction.LEFT));


        engine.addEntity(spider);
        player = new Player(spider);
    }

    @Override
    public void render(float delta) {
       engine.update(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
    }
}
