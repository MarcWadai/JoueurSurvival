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

    private static final float CAMERA_WIDTH = 320;
    private static final float CAMERA_HEIGHT = 480;

    private World world;
    private WorldRenderer worldRenderer;


    OrthographicCamera guiCam;
    Rectangle soundBounds;
    Rectangle playBounds;
    Rectangle highscoresBounds;
    Rectangle settingsBounds;
    Vector3 touchPoint;

    MyGdxGame game;

    public MainScreen(MyGdxGame myGdxGame){
        this.game = myGdxGame;

    }
    @Override
    public void show() {

        guiCam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        guiCam.position.set(CAMERA_WIDTH / 2, CAMERA_HEIGHT / 2, 0);
        soundBounds = new Rectangle(0, 0, 64, 64);
        playBounds = new Rectangle(CAMERA_WIDTH/2 - Assets.PLAY_WIDTH_CENTER/2 + 5, (3*CAMERA_HEIGHT/4 - Assets.PLAY_HEIGHT/2) - 2*Assets.PLAY_HEIGHT_CENTER/(Gdx.graphics.getHeight() / CAMERA_HEIGHT), Assets.PLAY_WIDTH, Assets.PLAY_HEIGHT);
        highscoresBounds = new Rectangle(CAMERA_WIDTH/2 - Assets.PLAY_WIDTH_CENTER/2 - 5, (2*CAMERA_HEIGHT/4 - Assets.PLAY_HEIGHT/2) - Assets.PLAY_HEIGHT_CENTER/(Gdx.graphics.getHeight() / CAMERA_HEIGHT), Assets.PLAY_WIDTH, Assets.PLAY_HEIGHT);
        settingsBounds = new Rectangle(CAMERA_WIDTH/2 - Assets.PLAY_WIDTH_CENTER/2, CAMERA_HEIGHT/4 - Assets.PLAY_HEIGHT/2, Assets.PLAY_WIDTH, Assets.PLAY_HEIGHT);
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
                System.out.println("Play");
                return;
            }
            if (highscoresBounds.contains(touchPoint.x, touchPoint.y)) {
               // Assets.playSound(Assets.clickSound);
              //  game.setScreen(new HighscoresScreen(game));
                game.setScreen(new HighScoreScreen(game));
                System.out.println("Highscore");
                return;
            }
            if (settingsBounds.contains(touchPoint.x, touchPoint.y)) {
                // Assets.playSound(Assets.clickSound);
                //  game.setScreen(new HighscoresScreen(game));
                System.out.println("Settings");
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
        game.batcher.draw(Assets.background, 0, 0,Assets.MENU_WIDTH, Assets.MENU_HEIGHT);
        game.batcher.draw(Assets.toilet3, CAMERA_WIDTH/2 - Assets.PLAY_WIDTH_CENTER/2, CAMERA_HEIGHT/4 - Assets.PLAY_HEIGHT/2, Assets.PLAY_WIDTH, Assets.PLAY_HEIGHT);
        game.batcher.draw(Assets.toilet2, CAMERA_WIDTH/2 - Assets.PLAY_WIDTH_CENTER/2 - 5, (2*CAMERA_HEIGHT/4 - Assets.PLAY_HEIGHT/2) - Assets.PLAY_HEIGHT_CENTER/(Gdx.graphics.getHeight() / CAMERA_HEIGHT), Assets.PLAY_WIDTH, Assets.PLAY_HEIGHT);
        game.batcher.draw(Assets.toilet1, CAMERA_WIDTH/2 - Assets.PLAY_WIDTH_CENTER/2 + 5, (3*CAMERA_HEIGHT/4 - Assets.PLAY_HEIGHT/2) - 2*Assets.PLAY_HEIGHT_CENTER/(Gdx.graphics.getHeight() / CAMERA_HEIGHT), Assets.PLAY_WIDTH, Assets.PLAY_HEIGHT);
        game.batcher.end();

    }

    @Override
    public void render (float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update();
        draw();
    }

    @Override
    public void pause () {
       // Settings.save();
    }
}
