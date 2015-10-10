package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.Assets;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controler.WorldRenderer;


/**
 * Created by rxcai on 10/10/15.
 */
public class GameScreen extends ScreenAdapter{

    public static final int GAME_RUNNING = 1;
    public static final int GAME_PAUSED = 2;
    public static final int GAME_OVER = 3;

    private World world;
    private WorldRenderer worldRenderer;
    Rectangle pauseBounds;
    MyGdxGame myGdxGame;
    int state ;
    int game_over = 0;

    public GameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        state = GAME_RUNNING;

    }


    @Override
    public void show() {
        world = new World();
        worldRenderer = new WorldRenderer(world, this);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 1f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        myGdxGame.batcher.begin();
        draw();
        switch (state) {

            case GAME_RUNNING:
                worldRenderer.render(delta);
                break;
            case GAME_PAUSED:
                presentPaused();
                break;
            case GAME_OVER:
                presentGameOver();
                break;
        }
        myGdxGame.batcher.end();


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

    public void draw(){

        myGdxGame.batcher.draw(Assets.pause, 0, 480 - 64, 50, 50);

    }

    private void presentPaused () {
        myGdxGame.batcher.draw(Assets.pauseMenu, 160 - 192 / 2, 240 - 96 / 2, 192, 96);
    }

    private void presentGameOver () {
        myGdxGame.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240 - 96 / 2, 160, 96);
        //glyphLayout.setText(Assets.font, scoreString);
        //Assets.font.draw(game.batcher, scoreString, 160 - glyphLayout.width / 2, 480 - 20);
    }




    public int getGame_over(){
        return game_over;
    }

    public void setSate(int sate){
        this.state = sate;
    }
}
