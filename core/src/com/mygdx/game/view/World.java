package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.Assets;
import com.mygdx.game.model.Ground;
import com.mygdx.game.model.Obstacle;

import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * Created by rxcai on 03/10/15.
 */
public class World {

    /** The blocks making up the world **/
    Array<com.mygdx.game.model.Ground> blocks = new Array();
    private Array<Rectangle> tile;
    /** Our player controlled hero **/
    Obstacle bob;
    SpriteBatch groundBach;
    TextureRegion textureGround;
    Sprite sprite;

    // Getters -----------
    public Array<com.mygdx.game.model.Ground> getBlocks() {
        return blocks;
    }
    public Obstacle getBob() {
        return bob;
    }
    // --------------------


    public World(SpriteBatch spriteBatch ) {
        tile = new Array<Rectangle>();
        createDemoWorld();
        textureGround = Assets.background;

    }


    private void createDemoWorld() {
        for (int i = 0; i < 30; i++) {
            blocks.add(new com.mygdx.game.model.Ground(new Vector2(i, 0)));
            tile.add(new Rectangle((float) i, (float)0, Ground.SIZE, Ground.SIZE ));
        }


    }

    public Array<Rectangle> getTile(){
        return this.tile;
    }

    public TextureRegion getBackgroundTexture(){
        return this.textureGround;
    }

}
