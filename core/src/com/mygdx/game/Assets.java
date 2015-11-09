package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by rxcai on 10/10/15.
 */
public class Assets {

    public static final int MENU_WIDTH = 418;
    public static final int MENU_HEIGHT = 552;
    public static final int PLAY_WIDTH = 150;
    public static final int PLAY_WIDTH_CENTER = 100;
    public static final int PLAY_HEIGHT = 131;
    public static final int PLAY_HEIGHT_CENTER = 80;

    public static Texture items;
    public static Texture menu;
    public static Texture toilet1;
    public static Texture toilet2;
    public static Texture toilet3;
    public static TextureRegion mainMenu;
    public static TextureRegion pauseMenu;
    public static TextureRegion ready;
    public static TextureRegion gameOver;
    public static TextureRegion highScores;
    public static TextureRegion background;
    public static TextureRegion play;
    public static TextureRegion settings;

    public static TextureRegion pause;


    public static Texture loadTexture (String file) {
        return new Texture(Gdx.files.internal(file));
    }

    public static void load () {

        items = loadTexture("images/items.png");
        menu = loadTexture("images/menu.png");
        toilet1 = loadTexture("images/play.png");
        toilet2 = loadTexture("images/scores.png");
        toilet3 = loadTexture("images/settings.png");
        mainMenu = new TextureRegion(items, 0, 224, 300, 110);
        pauseMenu = new TextureRegion(items, 224, 128, 192, 96);
        gameOver = new TextureRegion(items, 352, 256, 160, 96);
        pause = new TextureRegion(items, 64, 64, 64, 64);
        background = new TextureRegion(menu, 0, 0, MENU_WIDTH, MENU_HEIGHT);
        play = new TextureRegion(toilet1, 0, 0, PLAY_WIDTH, PLAY_HEIGHT);
        highScores = new TextureRegion(toilet2, 0, 0, PLAY_WIDTH, PLAY_HEIGHT);
        settings = new TextureRegion(toilet3, 0, 0, PLAY_WIDTH, PLAY_HEIGHT);
    }
}
