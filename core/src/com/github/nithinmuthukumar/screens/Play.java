package com.github.nithinmuthukumar.screens;

import box2dLight.PointLight;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import com.github.nithinmuthukumar.EntityFactory;
import com.github.nithinmuthukumar.Enums.Action;
import com.github.nithinmuthukumar.Enums.Direction;
import com.github.nithinmuthukumar.Player;
import com.github.nithinmuthukumar.components.*;
import com.github.nithinmuthukumar.systems.*;


import java.util.HashMap;


import static com.github.nithinmuthukumar.Globals.*;


public class Play implements Screen {
    public static int level;
    HashMap<Action,float[]> playerFrames = new HashMap<Action, float[]>();
        
    public Play(int level){
        Play.level = level;
    }


    @Override
    public void show() {
        Gdx.app.log("show","play");
        Sound sound;
        sound = Gdx.audio.newSound(Gdx.files.internal("xDeviruchi - Prepare for Battle! .wav"));
        sound.loop();
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                //the user data will always be an entity
                Entity a = (Entity) contact.getFixtureA().getUserData();
                Entity b = (Entity) contact.getFixtureB().getUserData();
                //set the collided entity of either entity
                if (a == null || b == null) {
                    return;
                }

                cmBody.get(a).collidedEntities.addLast(b);
                cmBody.get(b).collidedEntities.addLast(a);

            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        world.setContactFilter(new ContactFilter() {
            @Override
            public boolean shouldCollide(Fixture fixtureA, Fixture fixtureB) {
                Entity e1 = (Entity) fixtureA.getUserData();
                Entity e2 = (Entity) fixtureB.getUserData();

               if(fixtureA==null||fixtureB==null){
                   return true;
               }
               if((cmEnemy.has(e1)&&cmPlayer.has(e2))||(cmEnemy.has(e2)&& cmPlayer.has(e1))){
                   return false;

               }
               if((cmWeapon.has(e1))){
                   if((cmWeapon.get(e1).isEnemy&& cmEnemy.has(e2))||(!cmWeapon.get(e1).isEnemy&& cmPlayer.has(e2))){
                       return false;
                   }
               }else if(cmWeapon.has(e2)){
                   if((cmWeapon.get(e2).isEnemy&& cmEnemy.has(e1))||(!cmWeapon.get(e2).isEnemy&& cmPlayer.has(e1))){
                       return false;
                   }

               }


               return true;
            }
        });
        engine.addEntityListener(Family.all(CRemoval.class, CBody.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                world.destroyBody(cmBody.get(entity).body);
                entity.remove(CBody.class);
            }

            @Override
            public void entityRemoved(Entity entity) { }
        });
        engine.addEntityListener(Family.all(CRemoval.class, CLight.class).get(), new EntityListener() {
            @Override
            public void entityAdded(Entity entity) {
                cmLight.get(entity).light.dispose();
                entity.remove(CLight.class);
            }

            @Override
            public void entityRemoved(Entity entity) {

            }
        });

        SUI ui = new SUI(14);
        Gdx.input.setInputProcessor(ui.getStage());

        engine.addSystem(new SRender(11));
        engine.addSystem(new SAnimation(5));
        engine.addSystem(new SPhysics(8));
        engine.addSystem(new SPlayerMovement(4));
        //engine.addSystem(new SDebugRender(13));

        engine.addSystem(new SCollision(6));
        engine.addSystem(new SRemoval(10));
        engine.addSystem(new SDecay(9));
        engine.addSystem(new SDeath(9));
        engine.addSystem(new SFollow(10));
        engine.addSystem(ui);




        setupGame();
        switch (level){
            case 1:
                setupLevel1();
                break;
            case 2:
                setupLevel2();
                break;
            case 3:
                setupLevel3();
                break;
            case 4:
                setupLevel4();
                break;
        }







    }
    private void setupGame(){
        rayHandler.setAmbientLight(1f);

        HashMap<Action,float[]> spaceFrames = new HashMap<Action, float[]>();
        spaceFrames.put(Action.NONE,new float[]{4,0.9f});
        Entity background = engine.createEntity();

        background.add(new CRenderable(new Texture("space/NONE/1.png")))
                .add(new CTransform(0,0,-1,Gdx.graphics.getHeight(),Gdx.graphics.getHeight()))
                .add(new CAnimation("space/",spaceFrames))
                .add(new CState(Action.NONE,Direction.NONE));
        engine.addEntity(background);

        playerFrames.put(Action.IDLE,new float[]{7,0.12f});
        playerFrames.put(Action.RUN,new float[]{8,0.12f});
        playerFrames.put(Action.JUMP,new float[]{3,0.4f});
        playerFrames.put(Action.KNOCKBACK,new float[]{7,0.15f});
        playerFrames.put(Action.SLASH1,new float[]{8,0.12f});
        playerFrames.put(Action.SLASH2, new float[]{6,0.12f});
        playerFrames.put(Action.SLASH3, new float[]{5,0.12f});
        playerFrames.put(Action.IDLETRANSITION, new float[]{2,0.12f});
        playerFrames.put(Action.CROUCH,new float[]{6,0.4f});
        playerFrames.put(Action.SLAM,new float[]{5,0.21f});
        playerFrames.put(Action.VULNERABLE, new float[]{2,1});
        playerFrames.put(Action.DASH,new float[]{6,0.15f});
        playerFrames.put(Action.SLIDE,new float[]{4,0.12f});
        playerFrames.put(Action.SHOOT, new float[]{6,0.3f});


        Entity platform = engine.createEntity();
        Body platformBody = EntityFactory.bodyBuilder("StaticBody",0,-300/PPM);
        EntityFactory.createRectFixture(0,0,500/PPM,20/PPM,false,platform,platformBody,1,1);
        EntityFactory.createRectFixture(-500f/PPM,0,20/PPM,300/PPM,false,platform,platformBody,1,1);
        EntityFactory.createRectFixture(500f/PPM,0,20/PPM,300/PPM,false,platform,platformBody,1,1);
        platform.add(new CBody(platformBody,0))
                .add(new CTransform(0,0,0,1000,50))
                .add(new CRenderable(new Texture("platform.png")));
        engine.addEntity(platform);

        Entity avatar = engine.createEntity();

        Body body = EntityFactory.bodyBuilder("DynamicBody",400/PPM,0);
        body.setFixedRotation(true);
        EntityFactory.createRectFixture(0,-30f/PPM,15f/PPM,30f/PPM,false,avatar,body,2,1);

        avatar.add(new CRenderable(new Texture("player/original/1.png")))
                .add(new CTransform(400,0,0,120,120))
                .add(new CAnimation("player/",playerFrames))
                .add(new CBody(body,1.2f))
                .add(new CPlayer())
                .add(new CHealth(10))



                .add(new CState(Action.IDLE, Direction.RIGHT));


        engine.addEntity(avatar);

        player = new Player(avatar);
    }

    private void setupLevel4() {
        engine.addSystem(new SLaserEnemy(4));
        Entity playerMol = engine.createEntity();
        playerMol.add(new CRenderable(new Texture("mcinit4.png")))
                .add(new CTransform(0,0,10,50,50))
                .add(new CFollow(player.getEntity(),0,0,"mcfinal4.png"));
        engine.addEntity(playerMol);





        Entity enemy = engine.createEntity();

        Body enemyBody = EntityFactory.bodyBuilder("DynamicBody",-400/PPM,0);
        enemyBody.setFixedRotation(true);
        EntityFactory.createRectFixture(0,-30f/PPM,15f/PPM,30f/PPM,false,enemy,enemyBody,2,1);
        enemy.add(new CRenderable(new Texture("player/original/1.png")))
                .add(new CTransform(-400,0,0,120,120))
                .add(new CAnimation("player/",playerFrames))
                .add(new CBody(enemyBody,1.2f))
                .add(new CEnemy()).add(new CState(Action.IDLE,Direction.RIGHT))
                .add(new CHealth(10));
        engine.addEntity(enemy);

        Entity core = engine.createEntity();

        core.add(new CRenderable(new Texture("blueOrb.png")))
                .add(new CTransform(0,0,11,10,10))
                .add(new CFollow(enemy,20f,-10f,true));



        engine.addEntity(core);
        core = engine.createEntity();
        core.add(new CRenderable(new Texture("blueOrb.png")))
                .add(new CTransform(0,0,11,10,10))
                .add(new CFollow(enemy,-20f,-10f,true));

        engine.addEntity(core);


        Entity enemyMol = engine.createEntity();
        enemyMol.add(new CRenderable(new Texture("enemyinit3.png")))
                .add(new CTransform(0,0,10,50,50))
                .add(new CFollow(enemy,0,0,"enemyfinal3.png"));
        engine.addEntity(enemyMol);

    }

    private void setupLevel3() {
        engine.addSystem(new SDashEnemy(4));
        Entity playerMol = engine.createEntity();
        playerMol.add(new CRenderable(new Texture("mcinit3.png")))
                .add(new CTransform(0,0,10,50,50))
                .add(new CFollow(player.getEntity(),0,0,"mcfinal3.png"));
        engine.addEntity(playerMol);





        Entity enemy = engine.createEntity();

        Body enemyBody = EntityFactory.bodyBuilder("DynamicBody",-400/PPM,0);
        enemyBody.setFixedRotation(true);
        EntityFactory.createRectFixture(0,-30f/PPM,15f/PPM,30f/PPM,false,enemy,enemyBody,2,1);
        enemy.add(new CRenderable(new Texture("player/original/1.png")))
                .add(new CTransform(-400,0,0,120,120))
                .add(new CAnimation("player/",playerFrames))
                .add(new CBody(enemyBody,1.2f))
                .add(new CEnemy()).add(new CState(Action.IDLE,Direction.RIGHT))
                .add(new CHealth(10));
        engine.addEntity(enemy);

        Entity core = engine.createEntity();

        core.add(new CRenderable(new Texture("blueOrb.png")))
                .add(new CTransform(0,0,10,10,10))
                .add(new CFollow(enemy,10f,-30f,true));


        engine.addEntity(core);

        Entity enemyMol = engine.createEntity();
        enemyMol.add(new CRenderable(new Texture("enemyinit3.png")))
                .add(new CTransform(0,0,30,50,50))
                .add(new CFollow(enemy,0,0,"enemyfinal3.png"));
        engine.addEntity(enemyMol);
    }

    private void setupLevel1() {
        engine.addSystem(new SEnemyAI(4));

        Entity Nal = engine.createEntity();
        Nal.add(new CRenderable(new Texture("mcinit.png")))
                .add(new CTransform(0,0,10,50,50))
                .add(new CFollow(player.getEntity(),0,0,"mcfinal.png"));
        engine.addEntity(Nal);






        Entity enemy = engine.createEntity();

        Body enemyBody = EntityFactory.bodyBuilder("DynamicBody",-400/PPM,0);
        enemyBody.setFixedRotation(true);
        EntityFactory.createRectFixture(0,-30f/PPM,15f/PPM,30f/PPM,false,enemy,enemyBody,2,1);
        enemy.add(new CRenderable(new Texture("player/original/1.png")))
                .add(new CTransform(-400,0,0,120,120))
                .add(new CAnimation("player/",playerFrames))
                .add(new CBody(enemyBody,1.2f))
                .add(new CEnemy()).add(new CState(Action.IDLE,Direction.RIGHT))
                .add(new CHealth(10))
                .add(new CLight(new PointLight(rayHandler,100, Color.LIGHT_GRAY,1,0,0),enemyBody,0,-30f/PPM));

        engine.addEntity(enemy);

        Entity bromo = engine.createEntity();
        bromo.add(new CRenderable(new Texture("enemyinit.png")))
                .add(new CTransform(0,0,30,50,50))
                .add(new CFollow(enemy,0,0,"enemyfinal.png"));
        engine.addEntity(bromo);



    }
    private void setupLevel2(){
        engine.addSystem(new SEnemyAI(4));
        Entity playerMol = engine.createEntity();
        playerMol.add(new CRenderable(new Texture("mcinit2.png")))
                .add(new CTransform(0,0,10,50,50))
                .add(new CFollow(player.getEntity(),0,0,"mcfinal2.png"));
        engine.addEntity(playerMol);





        Entity enemy = engine.createEntity();

        Body enemyBody = EntityFactory.bodyBuilder("DynamicBody",-400/PPM,0);
        enemyBody.setFixedRotation(true);
        EntityFactory.createRectFixture(0,-30f/PPM,15f/PPM,30f/PPM,false,enemy,enemyBody,2,1);
        enemy.add(new CRenderable(new Texture("player/original/1.png")))
                .add(new CTransform(-400,0,0,120,120))
                .add(new CAnimation("player/",playerFrames))
                .add(new CBody(enemyBody,1.2f))
                .add(new CEnemy()).add(new CState(Action.IDLE,Direction.RIGHT))
                .add(new CHealth(10))
                .add(new CLight(new PointLight(rayHandler,100, Color.LIGHT_GRAY,1,0,0),enemyBody,0,-30f/PPM));
        engine.addEntity(enemy);

        Entity core = engine.createEntity();

        core.add(new CRenderable(new Texture("orb.png")))
                .add(new CTransform(0,0,10,10,10))
                .add(new CFollow(enemy,10f,-30f,true));


        engine.addEntity(core);

        Entity enemyMol = engine.createEntity();
        enemyMol.add(new CRenderable(new Texture("enemyinit2.png")))
                .add(new CTransform(0,0,30,50,50))
                .add(new CFollow(enemy,0,0,"enemyfinal2.png"));
        engine.addEntity(enemyMol);


        
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
