package com.mygdx.game.android.model;

/**
 * Created by rxcai on 03/10/15.
 */

    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;

    public class Obstacle {

        public enum State {
            IDLE, WALKING, JUMPING, DYING
        }

        public static final float SPEED = 2f;	// unit per second
        public static final float JUMP_VELOCITY = 1f;
        public static final float SIZEHEIGHT = 3f; // half a unit
        public static final float SIZEWIDTH = 1f;

        public static final float SIZEHEIGHT2 = 6f; // half a unit
        public static final float SIZEWIDTH2 = 1f;

        public final static float HOLE = 1.5f;



        private Vector2 	position = new Vector2();
        Vector2 	acceleration = new Vector2();
        Vector2 	velocity = new Vector2();
        private Rectangle 	bounds = new Rectangle();
        State		state = State.IDLE;

        public Obstacle(Vector2 position, float height, float width) {
            this.position = position;
            this.bounds.height = height;
            this.bounds.width = width;
        }

        public Vector2 getPosition(){
           return this.position;
        }

        public Rectangle getBounds(){
            return this.bounds;
        }
    }