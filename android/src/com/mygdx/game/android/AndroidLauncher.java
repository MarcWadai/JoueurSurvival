package com.mygdx.game.android;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mygdx.game.android.MyGdxGame;

import java.io.Serializable;

public class AndroidLauncher extends AndroidApplication  implements MyGdxGame.MyGameCallback, Serializable {
	MyGdxGame myGdxGame;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		myGdxGame = new MyGdxGame();
		//myGdxGame.setMyGameCallback(this);

		initialize(myGdxGame, config);

	}


	@Override
	public void onStartActivityHome() {
		Intent intent = new Intent(this, Home.class);
		intent.putExtra("Game",myGdxGame);
		startActivity(intent);
	}
}
