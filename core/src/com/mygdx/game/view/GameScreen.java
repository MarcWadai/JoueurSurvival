package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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
    public static final int GAME_RESTART = 4;

    private World world;
    private WorldRenderer worldRenderer;
    Rectangle pauseBounds;
    Rectangle pauseMenuBounds;
    Vector3 touchPoint;
    MyGdxGame myGdxGame;
    int state ;
    int game_over = 0;

    public GameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        state = GAME_RUNNING;
        touchPoint =new Vector3();
        pauseBounds = new Rectangle(64, 64, 64, 64);
        pauseMenuBounds = new Rectangle(165, 700, 300, 100);

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
        myGdxGame.batcher.end();

        switch (state) {

            case GAME_RUNNING:
                worldRenderer.render(delta);
                break;
            case GAME_PAUSED:
                myGdxGame.batcher.begin();
                presentPaused();
                myGdxGame.batcher.end();
                break;
            case GAME_OVER:
                myGdxGame.batcher.begin();
                presentGameOver();
                myGdxGame.batcher.end();
                break;
            case GAME_RESTART :
                myGdxGame.setScreen(new GameScreen(myGdxGame));
                System.out.println("restart in game screen");
                break;

        }



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
        myGdxGame.batcher.draw(Assets.gameOver, 160 - 160 / 2, 240/2 + 100, 160, 96);
        myGdxGame.batcher.draw(Assets.pauseMenu, 160 - 192 / 2, 240/2, 192, 96);
        //glyphLayout.setText(Assets.font, scoreString);
        //Assets.font.draw(game.batcher, scoreString, 160 - glyphLayout.width / 2, 480 - 20);
    }




    public int getGame_over(){
        return game_over;
    }

    public int getState(){
        return this.state;
    }

    public MyGdxGame getMyGdxGame(){
        return this.myGdxGame;
    }

    public Rectangle getPauseMenuBounds(){
        return pauseMenuBounds;
    }

    public Rectangle getPauseBounds(){
        return pauseBounds;
    }

    public void setSate(int sate){
        this.state = sate;
    }



}
