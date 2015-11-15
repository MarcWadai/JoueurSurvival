package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by thanh on 03/10/15.
 */
public class Ground {

   public static final float SIZE = 1f;
    public static final float SIZE_WIDTH = Gdx.graphics.getWidth();
    private Vector2 position = new Vector2();
    private Rectangle bounds = new Rectangle();

    private Texture textureGround;



    public Ground(Vector2 pos) {
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
        textureGround = new Texture(Gdx.files.internal("ground.png"));
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public Rectangle getBounds(){
        return this.bounds;
    }

    public Texture getTextureGround(){return this.textureGround;};


}
