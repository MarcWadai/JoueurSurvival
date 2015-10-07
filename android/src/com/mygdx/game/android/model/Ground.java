package com.mygdx.game.android.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by rxcai on 03/10/15.
 */
public class Ground {

   public static final float SIZE = 1f;

    private Vector2 position = new Vector2();
    private Rectangle bounds = new Rectangle();

    public Ground(Vector2 pos) {
        this.position = pos;
        this.bounds.width = SIZE;
        this.bounds.height = SIZE;
    }

    public Vector2 getPosition(){
        return this.position;
    }

    public Rectangle getBounds(){
        return this.bounds;
    }
}
