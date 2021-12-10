package com.github.nithinmuthukumar.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.github.nithinmuthukumar.Globals.game;
import static com.github.nithinmuthukumar.Globals.skin;

public class LevelSelect implements Screen {
    private Stage stage;

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Label title = new Label("Chemical Clash",skin,"default");
        title.setPosition(Gdx.graphics.getWidth()/2-100,Gdx.graphics.getHeight()*7f/8f);
        title.setFontScale(2f);
        Table table = new Table();

        for(int i =1;i<=4;i++){
            final TextButton levelButton = new TextButton("Level "+i, skin,"default");
            final int finalI = i;
            levelButton.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new PreLevel(finalI));

                    super.clicked(event, x, y);
                }
            });

            table.add(levelButton);

        }
        table.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()*2f/4f);
        TextButton loreButton = new TextButton("Explore the lore",skin,"default");
        loreButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new Lore());
                super.clicked(event, x, y);
            }
        });
        loreButton.setPosition(Gdx.graphics.getWidth()/2f,Gdx.graphics.getHeight()/2-200);
        stage.addActor(loreButton);

        stage.addActor(table);
        stage.addActor(title);
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
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
