package com.github.nithinmuthukumar;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.github.nithinmuthukumar.screens.LevelSelect;
import com.github.nithinmuthukumar.screens.Play;

import static com.github.nithinmuthukumar.Globals.batch;

public class SpidGame extends Game {

	@Override
	public void create () {
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
		Gdx.input.setCatchKey(Input.Keys.SPACE, true);
		Globals.game = this;
		Globals.mainCamera = new OrthographicCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		setScreen(new LevelSelect());
	}

	@Override
	public void render () {
		if(Gdx.input.isKeyJustPressed(Input.Keys.F))
			Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());

		Gdx.gl.glClearColor(0,0,0,0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


		super.render();
	}

	@Override
	public void dispose () {
	}
}
