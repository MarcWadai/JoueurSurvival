package com.mygdx.game.android.controler;

/**
 * Created by rxcai on 03/10/15.
 */


    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.mygdx.game.android.model.Obstacle;
    import com.mygdx.game.android.view.World;



public class WorldRenderer {

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 10f;
    private static final float SPEED_OBSTACLE = 2;
    private static final float MOVING_RANGE = 0.05f;
    private final static  float OUT_RANGE = 15;

    int timer = 0;


    private World world;
    private OrthographicCamera cam;
    private Obstacle obstacle;
    private Obstacle obstacle2;

    private Obstacle test;
    private Boolean collide = false;

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Textures **/
    private Texture bobTexture;
    private Texture bobTexture2;
    private Texture blockTexture;

    private SpriteBatch spriteBatch;
    private boolean debug = false;
    private int width;
    private int height;
    private float ppuX;	// pixels per unit on the X axis
    private float ppuY;	// pixels per unit on the Y axis
    private float obstacle2Y;

    public void setSize (int w, int h) {
        this.width = w;
        this.height = h;
        ppuX = (float)width / CAMERA_WIDTH;
        ppuY = (float)height / CAMERA_HEIGHT;
    }


    public WorldRenderer(World world) {
        this.world = world;
        this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
        this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
        this.cam.update();

        obstacle = new Obstacle(new Vector2(10, 1),Obstacle.SIZEWIDTH,Obstacle.SIZEHEIGHT);
        // obstacle.bounds.set(obstacle.position.x, obstacle.position.y, Obstacle.SIZEWIDTH, Obstacle.SIZEHEIGHT);
        obstacle2Y = obstacle.getPosition().y +Obstacle.SIZEHEIGHT +  Obstacle.HOLE;
        obstacle2 = new Obstacle(new Vector2((float)10,obstacle2Y),Obstacle.SIZEWIDTH2, Obstacle.SIZEHEIGHT2);
        test = new Obstacle(new Vector2(3, (float) 4.3), com.mygdx.game.model.Ground.SIZE , com.mygdx.game.model.Ground.SIZE );
        test.getBounds().setX(3);
        test.getBounds().setWidth(com.mygdx.game.model.Ground.SIZE * ppuX);
        test.getBounds().setHeight(com.mygdx.game.model.Ground.SIZE * ppuY);
        debug = true;
        spriteBatch = new SpriteBatch();
        loadTextures();
    }

    private void loadTextures() {
        bobTexture = new  Texture(Gdx.files.internal("images/rect.jpg"));
        bobTexture2 = new  Texture(Gdx.files.internal("images/rect.jpg"));
     //   blockTexture = new Texture(Gdx.files.internal("images/block.png"));
    }


    public void render() {
        collisionDetection();
        spriteBatch.begin();
        drawBlocks();
        drawBob();
        spriteBatch.end();
        updateObstable();

        if (debug)
            drawDebug();
    }


    // Modifie la position de l'obstacle, sa vitesse et tourne en boucle
    public void updateObstable(){

        if ( !(obstacle.getPosition().x < 0 && collide)) {
            if (obstacle.getPosition().x < 0) {
                obstacle.getPosition().x = 10;
                obstacle2.getPosition().x = 10;
            }
            else{
                if (timer == SPEED_OBSTACLE) {
                    obstacle.getPosition().x = (float) obstacle.getPosition().x - (float) MOVING_RANGE;
                    obstacle.getBounds().x = (float) obstacle.getPosition().x - (float) MOVING_RANGE;
                    obstacle2.getPosition().x = (float) obstacle2.getPosition().x - (float) MOVING_RANGE;
                    obstacle2.getBounds().x = (float) obstacle2.getPosition().x - (float) MOVING_RANGE;
                    timer = 0;
                }
            }
            timer++;
        }


    }

    public void collisionDetection(){

        float topObstacle1 = (obstacle.getPosition().y) + (Obstacle.SIZEHEIGHT);
        float bottomObstacle2 = (obstacle2.getPosition().y);
        System.out.println( "test " +test.getPosition().y);
        System.out.println("obstacle 2 " +bottomObstacle2);
        System.out.println("obstacle " + topObstacle1);
        System.out.println(collide);
        //System.out.println();
        if ( !(((test.getPosition().y)> (topObstacle1)) && ((test.getPosition().y) < (bottomObstacle2) ) )) {
            if ((test.getPosition().x >(obstacle.getPosition().x - com.mygdx.game.model.Ground.SIZE) )) {
                test.getPosition().x = obstacle.getPosition().x - com.mygdx.game.model.Ground.SIZE;
                collide = true;
                //  System.out.println("enter collision " );
            }
        }
        else {
            //System.out.println("didnt enter collision");
            collide = false;
        }
    }

    private void drawBlocks() {
        spriteBatch.draw(bobTexture, test.getPosition().x * ppuX, test.getPosition().y * ppuY, com.mygdx.game.model.Ground.SIZE * ppuX, com.mygdx.game.model.Ground.SIZE * ppuY);
    }

    private void drawBob() {

        spriteBatch.draw(bobTexture, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, Obstacle.SIZEWIDTH * ppuX, Obstacle.SIZEHEIGHT * ppuY);
        spriteBatch.draw(bobTexture2, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, Obstacle.SIZEWIDTH2 * ppuX, Obstacle.SIZEHEIGHT2 * ppuY);
    }

    private void drawDebug() {
        // render blocks
        debugRenderer.setProjectionMatrix(cam.combined);
        debugRenderer.begin(ShapeType.Line);
        for (com.mygdx.game.model.Ground block : world.getBlocks()) {
            Rectangle rect = block.getBounds();
            float x1 = block.getPosition().x + rect.x;
            float y1 = block.getPosition().y + rect.y;
            debugRenderer.setColor(new Color(1, 0, 0, 1));
            debugRenderer.rect(x1, y1, rect.width, rect.height);
        }
        // render Bob
        Obstacle bob = world.getBob();
        Rectangle rect = bob.getBounds();
        float x1 = bob.getPosition().x + rect.x;
        float y1 = bob.getPosition().y + rect.y;
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(x1, y1, rect.width, rect.height);
        debugRenderer.end();
    }
}