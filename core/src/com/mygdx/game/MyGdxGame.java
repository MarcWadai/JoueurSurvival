package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.view.MainScreen;

public class MyGdxGame extends Game{
	public SpriteBatch batcher;


	@Override
	public void create () {

		batcher = new SpriteBatch();
		Assets.load();
		setScreen(new MainScreen(this));

	}



}
