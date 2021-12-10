package com.github.nithinmuthukumar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.github.nithinmuthukumar.Globals.game;

public class PreLevel implements Screen {
    private int level;
    private Stage stage;
    private Image image1;
    private Image image2;
    private Image image3;
    public PreLevel(int level){
        this.level = level;

    }
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        image1 = new Image(new TextureRegion(new Texture("Pre_level"+level+"_chemclash/1.png")));
        image1.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);

        image2 = new Image(new TextureRegion(new Texture("Pre_level"+level+"_chemclash/2.png")));
        image2.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);

        image3 = new Image(new TextureRegion(new Texture("Pre_level"+level+"_chemclash/3.png")));
        image3.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);


        stage.addActor(image1);
        image1.setVisible(true);
        stage.addActor(image2);
        image2.setVisible(false);
        stage.addActor(image3);
        image3.setVisible(false);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            if(image1.isVisible()){
                image1.setVisible(false);
                image2.setVisible(true);
            }
            else if(image2.isVisible()){
                image2.setVisible(false);
                image3.setVisible(true);
            }
            else if(image3.isVisible()){
                game.setScreen(new Play(1));
            }
        }
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

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
