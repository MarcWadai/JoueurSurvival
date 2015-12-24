package com.mygdx.game.model;

/**
 * Created by rxcai on 03/10/15.
 */

    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.mygdx.game.Assets;

public class Obstacle {

        public enum State {
            IDLE, WALKING, JUMPING, DYING
        }

        public static final float SPEED = 2f;	// unit per second
        public static final float JUMP_VELOCITY = 1f;
        public static final float textureFillParameterWidth = 1.5f;
        public static final float textureFillParameterHeight = 1.2f;
        public static final float textureFillParameterX = 1f;
        public static final float textureFillParameterY = 0.2f;
        public static final float SIZEHEIGHT = (Assets.obstacleBackground11.getHeight())/100f; // half a unit, 100 because the image size is ..
        public static final float SIZEWIDTH = 1f;//(Assets.obstacleBackground11.getWidth() )/100f;


        public static final float SIZEHEIGHT2 = 6f; // half a unit
        public static final float SIZEWIDTH2 = 0.5f;

        public final static float HOLE = 3.5f;

        public static final float OBSTACLE11_HEIGHT = Assets.obstacleBackground11.getHeight()/100f;
        public static final float OBSTACLE12_HEIGHT = Assets.obstacleBackground12.getHeight()/100f;
        public static final float OBSTACLE13_HEIGHT = Assets.obstacleBackground13.getHeight()/100f;
        public static final float OBSTACLE14_HEIGHT = Assets.obstacleBackground14.getHeight()/100f;
        public static final float OBSTACLE15_HEIGHT = Assets.obstacleBackground15.getHeight()/100f;
        public static final float OBSTACLE16_HEIGHT = Assets.obstacleBackground16.getHeight()/100f;
        public static final float OBSTACLE17_HEIGHT = Assets.obstacleBackground17.getHeight()/100f;


        public static final int numberObstale = 8;





        private Vector2 position = new Vector2();
        private Rectangle bounds = new Rectangle();
        private Texture obstacleTexture;

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

        public void setObstacleTexture(Texture texture){
            obstacleTexture = texture;
        }
    }