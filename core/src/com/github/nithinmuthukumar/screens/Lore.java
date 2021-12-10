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

public class Lore implements Screen {

    private Stage stage;
    private Image image1;
    private Image image2;
    private Image image3;
    private Image image4;
    private Image image5;
    private Image image6;
    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        image1 = new Image(new TextureRegion(new Texture("SciComm_Background_Info/1.png")));
        image1.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);

        image2 = new Image(new TextureRegion(new Texture("SciComm_Background_Info/2.png")));
        image2.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        image3 = new Image(new TextureRegion(new Texture("SciComm_Background_Info/3.png")));
        image3.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        image4 = new Image(new TextureRegion(new Texture("SciComm_Background_Info/4.png")));
        image4.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        image5 = new Image(new TextureRegion(new Texture("SciComm_Background_Info/5.png")));
        image5.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);
        image6 = new Image(new TextureRegion(new Texture("SciComm_Background_Info/6.png")));
        image6.setPosition(Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/4);

        stage.addActor(image1);
        image1.setVisible(true);
        stage.addActor(image2);
        image2.setVisible(false);
        stage.addActor(image3);
        image3.setVisible(false);
        stage.addActor(image4);
        image4.setVisible(false);
        stage.addActor(image5);
        image5.setVisible(false);
        stage.addActor(image6);
        image6.setVisible(false);

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
                image3.setVisible(false);
                image4.setVisible(true);
            }else if(image4.isVisible()){
                image4.setVisible(false);
                image5.setVisible(true);
            }else if(image5.isVisible()){
                image5.setVisible(false);
                image6.setVisible(true);
            }else if(image6.isVisible()){
                game.setScreen(new LevelSelect());
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
