package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.mygdx.game.controler.WorldRenderer;

/**
 * Created by rxcai on 03/10/15.
 */


public class GameScreen implements Screen{

    private World world;
    private WorldRenderer worldRenderer;



    @Override
    public void show() {

        world = new World();
        worldRenderer = new WorldRenderer(world);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        worldRenderer.render();
    }

    public void resize(int width, int height) {
        worldRenderer.setSize(width, height);
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
