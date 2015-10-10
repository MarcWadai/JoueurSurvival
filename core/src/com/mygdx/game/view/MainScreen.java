package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Assets;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controler.WorldRenderer;

/**
 * Created by rxcai on 03/10/15.
 */


public class MainScreen extends ScreenAdapter{

    private World world;
    private WorldRenderer worldRenderer;

    OrthographicCamera guiCam;
    Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle highscoresBounds;
    Vector3 touchPoint;

    MyGdxGame game;

    public MainScreen(MyGdxGame myGdxGame){
        this.game = myGdxGame;

    }
    @Override
    public void show() {

        guiCam = new OrthographicCamera(320, 480);
        guiCam.position.set(320 / 2, 480 / 2, 0);
        soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(160 - 150, 200 + 18, 300, 36);
        highscoresBounds = new Rectangle(160 - 150, 200 - 18, 300, 36);
        touchPoint = new Vector3();


        //world = new World();
        //worldRenderer = new WorldRenderer(world);
    }




    public void update () {
        if (Gdx.input.justTouched()) {
            guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (playBounds.contains(touchPoint.x, touchPoint.y)) {
               // Assets.playSound(Assets.clickSound);
                game.setScreen(new GameScreen(game));
                return;
            }
            if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
               // Assets.playSound(Assets.clickSound);
              //  game.setScreen(new HighscoresScreen(game));
                return;
            }

        }
    }


    public void draw () {
        GL20 gl = Gdx.gl;
        gl.glClearColor(1, 0, 0, 1);
        gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        guiCam.update();
        game.batcher.setProjectionMatrix(guiCam.combined);

        game.batcher.enableBlending();
        game.batcher.begin();
        game.batcher.draw(Assets.mainMenu, 10, 200 - 110 / 2, 300, 110);
        game.batcher.end();
    }

    @Override
    public void render (float delta) {
        update();
        draw();
    }

    @Override
    public void pause () {
       // Settings.save();
    }
}
