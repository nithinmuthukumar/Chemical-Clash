package com.github.nithinmuthukumar.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.github.nithinmuthukumar.components.CHealth;
import com.github.nithinmuthukumar.screens.LevelSelect;
import com.github.nithinmuthukumar.screens.Play;

import java.util.ArrayList;

import static com.github.nithinmuthukumar.Globals.*;

public class SUI extends IteratingSystem {
    private Stage stage;
    private ProgressBar enemyHealth;
    private ProgressBar playerHealth;
    private TextButton levelScreenButton;
    private ArrayList<Image> postLevel;
    public SUI(int priority) {
        super(Family.all(CHealth.class).get(), priority);
        postLevel = new ArrayList<Image>();

        postLevel.add(new Image(new TextureRegion(new Texture("Post_level1_chemclash.png"))));
        postLevel.add(new Image(new TextureRegion(new Texture("Post_level2_chemclash.png"))));
        postLevel.add(new Image(new TextureRegion(new Texture("Post_level3_chemclash.png"))));
        postLevel.add(new Image(new TextureRegion(new Texture("Post_level4_chemclash.png"))));


        stage = new Stage(new ScreenViewport());
        playerHealth = new ProgressBar(0.0f,10f,1,false,skin);
        playerHealth.setPosition(100,Gdx.graphics.getHeight()-300);
        Label playerLabel = new Label("Player Health",skin);
        playerLabel.setPosition(100,Gdx.graphics.getHeight()-325);

        enemyHealth = new ProgressBar(0.0f,10f,1,false,skin);
        enemyHealth.setPosition(100,Gdx.graphics.getHeight()-425);
        final Label enemyLabel = new Label("Enemy Health",skin);
        enemyLabel.setPosition(100,Gdx.graphics.getHeight()-550);

        levelScreenButton = new TextButton("Levels",skin);
        levelScreenButton.setPosition(Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-300);
        levelScreenButton.setVisible(false);
        levelScreenButton.addListener(
                new ClickListener(){
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        world = new World(new Vector2(0,-0.09f*PPM), false);
                        engine = new Engine();
                        game.setScreen(new LevelSelect());

                    }
                }
        );

        stage.addActor(playerHealth);
        stage.addActor(playerLabel);
        stage.addActor(enemyHealth);
        stage.addActor(enemyLabel);
        stage.addActor(levelScreenButton);


    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        CHealth health = cmHealth.get(entity);
        if(cmPlayer.has(entity)){
            playerHealth.setValue(health.life);
        }
        else{
            enemyHealth.setValue(health.life);
            if(health.life==0){
                stage.addActor(postLevel.get(Play.level));


                levelScreenButton.setVisible(true);
            }
        }



    }

    @Override
    public void update(float deltaTime) {

        stage.act();
        stage.draw();
        super.update(deltaTime);


    }

    public Stage getStage() {
        return stage;
    }
}
