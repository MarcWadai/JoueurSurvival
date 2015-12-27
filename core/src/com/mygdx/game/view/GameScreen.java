package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.mygdx.game.Assets;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.controler.WorldRenderer;
import com.mygdx.game.model.ScoreLabel;


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

    ShapeRenderer debugRenderer;

    // Score and best score
    ScoreLabel scoreLabel;
    BitmapFont bitmapFont;
    Preferences preference;
    boolean enterSave = false;

    public GameScreen(MyGdxGame myGdxGame){
        this.myGdxGame = myGdxGame;
        state = GAME_RUNNING;
        touchPoint =new Vector3();
        pauseBounds = new Rectangle(64, 64, 64, 64);
        pauseMenuBounds = new Rectangle(165, 700, 400, 100);
        bitmapFont = new BitmapFont(Gdx.files.internal("images/fontscore.fnt"),Gdx.files.internal("images/fontscore.png"),false);
        Label.LabelStyle style = new Label.LabelStyle(bitmapFont, Color.WHITE);
        //scoreLabel =new ScoreLabel("Score", style);

        if(preference == null) {
            preference = Gdx.app.getPreferences("preferencesJoueur");
            preference.flush();
            preference.putInteger("score", 3);
            preference.putBoolean("hey", true);
            //System.out.println("prefernce initial " + preference.getInteger("score") + ", bool " + preference.getBoolean("hey"));
            //System.out.println("pref ini "+Gdx.app.getPreferences("preferencesJoueur"));
        }

    }


    @Override
    public void render(float delta) {

        super.render(delta);
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        switch (state) {

            case GAME_RUNNING:
                worldRenderer.render(delta);
                int score = worldRenderer.getScore() - 3;
                myGdxGame.batcher.begin();
                draw();
                bitmapFont.draw(myGdxGame.batcher, String.valueOf(score), 2 * worldRenderer.getppux(), 4 * worldRenderer.getppuy());
                bitmapFont.draw(myGdxGame.batcher, String.valueOf(preference.getInteger("score")), 3 * worldRenderer.getppux(), 4 * worldRenderer.getppuy());

                myGdxGame.batcher.end();
                break;
            case GAME_PAUSED:
                myGdxGame.batcher.begin();
                presentPaused();
                myGdxGame.batcher.end();
                break;
            case GAME_OVER:
                worldRenderer.render(delta);
                myGdxGame.batcher.begin();
                presentGameOver();
                myGdxGame.batcher.end();
                if (enterSave == false) {
                    if (preference.getInteger("score") < worldRenderer.getScore() - 3) {
                        saveBestScore();
                    }
                    enterSave = true;
                }
                break;
            case GAME_RESTART :
                enterSave = false;
                myGdxGame.setScreen(new GameScreen(myGdxGame));
                System.out.println("restart in game screen");
                break;

        }



    }


    @Override
    public void show() {
        world = new World(myGdxGame.batcher);
        worldRenderer = new WorldRenderer(world, this);
        worldRenderer.setScore(0);

    }

    public void saveBestScore(){
        //if (preference.contains("score") == false){
            int s = worldRenderer.getScore()-3;
            preference.putInteger("score",s);
            preference.flush();
            System.out.println("actual score "+s);
            System.out.println("saving the score, before there was no score "+preference.getInteger("score"));

        //}
    }

    public void resize(int width, int height) {
        worldRenderer.setSize(width, height);
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


    private void drawDebug() {
        // render blocks
       /* debugRenderer.setProjectionMatrix(myGdxGame.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);

        debugRenderer.setColor(new Color(0, 0, 0, 1));
        debugRenderer.rect(pauseMenuBounds.x, pauseMenuBounds.y, pauseMenuBounds.width, pauseMenuBounds.height);
        debugRenderer.end();*/
    }

}
