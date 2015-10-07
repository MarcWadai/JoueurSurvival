package com.mygdx.game.android;

import android.util.Log;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.android.view.GameScreen;

import java.io.Serializable;

public class MyGdxGame extends Game implements Serializable {

	MyGdxGame myGdxGame;
	boolean canProceed = false;
	public interface MyGameCallback {
		public void onStartActivityHome();

	}

	private MyGameCallback myGameCallback;

	public void setMyGameCallback(MyGameCallback callback) {
		myGameCallback = callback;
		myGdxGame.setScreen(new GameScreen());
	}


	@Override
	public void create () {
		myGdxGame = this;
		//myGameCallback.onStartActivityHome();
		myGdxGame.setScreen(new GameScreen());
	}

	public void gameScreen(){
/*		new Thread(new Runnable() {

			@Override
			public void run() {
				myGdxGame.resume();*/
					myGdxGame.setScreen(new GameScreen());

	//		}
	//	}).start();

	}


	public void setCanProceed(boolean proceed){
		this.canProceed = proceed;
	}

}
