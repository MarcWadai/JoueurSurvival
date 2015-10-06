package com.mygdx.game.controler;

/**
 * Created by marc on 03/10/15.
 */


    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.g2d.TextureAtlas;
    import com.badlogic.gdx.graphics.g2d.TextureRegion;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.mygdx.game.model.Obstacle;
    import com.mygdx.game.model.Player;
    import com.mygdx.game.view.World;

    import java.util.Random;


public class WorldRenderer {

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 10f;
    private static final float SPEED_OBSTACLE = 2;
    private static final float MOVING_RANGE = 0.07f;
    private final static  float OUT_RANGE = 15;
    private final static  float OUT_RANGE_X = 9;
    private static final float UNIT_SCALE = 1/16f;

    Random random;
    int obstacleSelect;
    boolean finishTour = true;

    int timer = 0;

    private World world;
    private Player player;
    private OrthographicCamera cam;
    private Obstacle obstacle;
    private Obstacle obstacle2;

    private Obstacle test;
    private Boolean collide = false;

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Player textures **/
    private TextureRegion playerIdleLeft;
    private TextureRegion playerIdleRight;
    private TextureRegion playerJumpLeft;
    private TextureRegion playerJumpRight;
    private TextureRegion playerFrame;


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
        random = new Random();
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
        player = new Player();
        loadPlayerTextures();
        //Gdx.input.setInputProcessor();

        player.setPosition(new Vector2(3, (float)4.3));
        player.setWidth(UNIT_SCALE * playerIdleRight.getRegionWidth());
        player.setHeight(UNIT_SCALE * playerIdleRight.getRegionHeight());
    }

    private void loadTextures() {
        bobTexture = new  Texture(Gdx.files.internal("images/rect.jpg"));
        bobTexture2 = new  Texture(Gdx.files.internal("images/rect.jpg"));
     //   blockTexture = new Texture(Gdx.files.internal("images/block.png"));
    }


    public void render() {

        obstacleSelect = random.nextInt(Obstacle.numberObstale)+1;
        System.out.println("obstacle number " + obstacleSelect);

        if ( finishTour) {
                switch (obstacleSelect){
                    case 1 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE11_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;
                    case 2 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE12_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;
                    case 3 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE13_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;

                    case 4 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE14_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;

                    case 5 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE15_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;
                    case 6 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE16_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;
                    case 7 :
                        obstacle.getPosition().x = OUT_RANGE_X;
                        obstacle2.getPosition().x = OUT_RANGE_X;
                        obstacle.setHeight(Obstacle.OBSTACLE17_HEIGHT);
                        obstacle2.setY(obstacle.getPosition().y +obstacle.getBounds().height +  Obstacle.HOLE);
                        break;

            }
        }


        //collisionDetection();
        spriteBatch.begin();
        //drawBlocks();
        drawBob();
        //drawPlayer();
        spriteBatch.end();
        updateObstable();

        if (debug)
            drawDebug();
    }


    // Modifie la position de l'obstacle, sa vitesse et tourne en boucle
    public void updateObstable(){

        if ( !(obstacle.getPosition().x < 0 && collide)) {
            if (obstacle.getPosition().x < 0) {
                obstacle.getPosition().x = OUT_RANGE_X;
                obstacle2.getPosition().x = OUT_RANGE_X;
                finishTour = true;
            }
            else{
                if (timer == SPEED_OBSTACLE) {
                    obstacle.getPosition().x = (float) obstacle.getPosition().x - (float) MOVING_RANGE;
                    obstacle.getBounds().x = (float) obstacle.getPosition().x - (float) MOVING_RANGE;
                    obstacle2.getPosition().x = (float) obstacle2.getPosition().x - (float) MOVING_RANGE;
                    obstacle2.getBounds().x = (float) obstacle2.getPosition().x - (float) MOVING_RANGE;
                    timer = 0;
                    finishTour = false;
                }
            }
            timer++;
        }


    }

    public void collisionDetection(){

        float topObstacle1 = (obstacle.getPosition().y) + (Obstacle.SIZEHEIGHT);
        float bottomObstacle2 = (obstacle2.getPosition().y);
        System.out.println( "player " +player.getPosition().y);
        System.out.println("obstacle 2 " +bottomObstacle2);
        System.out.println("obstacle " + topObstacle1);
        System.out.println(collide);
        //System.out.println();
        if ( !(((player.getPosition().y)> (topObstacle1)) && ((player.getPosition().y) < (bottomObstacle2) ) )) {
            if ((player.getPosition().x >(obstacle.getPosition().x - com.mygdx.game.model.Ground.SIZE) )) {
                player.setPosition(new Vector2((float)(obstacle.getPosition().x - com.mygdx.game.model.Ground.SIZE), (float)(player.getPosition().y)));
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

        spriteBatch.draw(bobTexture, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);
        spriteBatch.draw(bobTexture2, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, obstacle2.getBounds().width * ppuX, obstacle2.getBounds().height* ppuY);
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

    public void loadPlayerTextures(){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("solbrain.pack"));
        /* Standing */
        playerIdleLeft = atlas.findRegion("1");

        playerIdleRight = new TextureRegion(playerIdleLeft);
        playerIdleRight.flip(true, false);

        /* Jumping */
        playerJumpLeft = atlas.findRegion("3");
        playerJumpRight = new TextureRegion(playerJumpLeft);
        playerJumpRight.flip(true, false);

    }

    public void drawPlayer(){

        playerFrame = playerIdleRight;

        if (player.getState() == Player.State.Jumping) {

            playerFrame = player.isFacingRight() ? playerJumpRight : playerJumpLeft;

        } else if (player.getState() == Player.State.Falling) {

            playerFrame = player.isFacingRight() ? playerJumpRight : playerJumpLeft;

        } else if (player.getState() == Player.State.Standing) {

            playerFrame = player.isFacingRight() ? playerIdleRight : playerIdleLeft;

        }

        spriteBatch.draw(playerFrame, player.getPosition().x * ppuX, (com.mygdx.game.model.Ground.SIZE*ppuY), com.mygdx.game.model.Ground.SIZE * ppuX, com.mygdx.game.model.Ground.SIZE * ppuY);
    }
}