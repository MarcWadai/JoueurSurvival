package com.mygdx.game.model;

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

        public static final float SIZEHEIGHT2 = 8f; // half a unit
        public static final float SIZEWIDTH2 = 1f;

        public final static float HOLE = 1.5f;

        public static final float OBSTACLE11_HEIGHT = 1f;
        public static final float OBSTACLE12_HEIGHT = 2f;
        public static final float OBSTACLE13_HEIGHT = 3f;
        public static final float OBSTACLE14_HEIGHT = 4f;
        public static final float OBSTACLE15_HEIGHT = 5f;
        public static final float OBSTACLE16_HEIGHT = 6f;
        public static final float OBSTACLE17_HEIGHT = 7f;


        public static final int numberObstale = 7;


        private Vector2 	position = new Vector2();
        Vector2 	acceleration = new Vector2();
        Vector2 	velocity = new Vector2();
        private Rectangle 	bounds = new Rectangle();
        State		state = State.IDLE;

        public Obstacle(Vector2 position, float width,float height) {
            this.position = position;
            this.bounds.height = height;
            this.bounds.width = width;
        }


        public Vector2 getPosition(){
           return this.position;
        }

        public void setPosition(Vector2 pos){
            this.position = pos;
        }

        public void setX(float x){
            this.position.x = x;
        }

        public void setY(float y){
            this.position.y = y;
        }

        public void setHeight(float heightO){
            this.bounds.height = heightO;
        }


        public Rectangle getBounds(){
            return this.bounds;
        }
    }