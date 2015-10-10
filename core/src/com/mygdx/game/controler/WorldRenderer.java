package com.mygdx.game.controler;

/**
 * Created by thanh on 03/10/15.
 */


    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.Input;
    import com.badlogic.gdx.InputProcessor;
    import com.badlogic.gdx.graphics.Color;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.g2d.TextureAtlas;
    import com.badlogic.gdx.graphics.g2d.TextureRegion;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
    import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.utils.Array;
    import com.badlogic.gdx.utils.Pool;
    import com.mygdx.game.MyGdxGame;
    import com.mygdx.game.model.Ground;
    import com.mygdx.game.model.Obstacle;
    import com.mygdx.game.model.Player;
    import com.mygdx.game.view.GameScreen;
    import com.mygdx.game.view.World;
    import java.util.Random;



public class WorldRenderer implements InputProcessor{

    private static final float CAMERA_WIDTH = 10f;
    private static final float CAMERA_HEIGHT = 10f;
    private static final float SPEED_OBSTACLE = 2;
    private static final float MOVING_RANGE = 0.07f;
    private final static  float OUT_RANGE = 15;
    private final static  float OUT_RANGE_X = 9;
    private static final float UNIT_SCALE = 1/25f;
    private static final long MAX_TIME_PRESS = 1000;
    private static final long MIN_TIME_PRESS = 200;
    private static final long TILEWIDTH = 1;
    float timer = 0;

    GameScreen gameScreen;


    private World world;
    private Player player;
    private OrthographicCamera cam;

    /** Obstacle and collision variables**/
    private Obstacle obstacle;
    private Obstacle obstacle2;
    private Obstacle test;
    private Boolean collide = false;
    Random random;
    int obstacleSelect;
    boolean finishTour = true;

    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Player textures **/
    private TextureRegion playerIdleLeft;
    private TextureRegion playerIdleRight;
    private TextureRegion playerJumpLeft;
    private TextureRegion playerJumpRight;
    private TextureRegion playerFrame;

    /** Jumping variables**/
    private boolean isClicked = false;
    private long pressTime = 0l;
    private long releaseTime = 0l;
    private long duration = 0l;
    private boolean jumpingPressed;
    private long timer1 = 0l;
    private long timer2 = 0l;
    private long timer3 = 0l;
    private long timer4 = 0l;
    private long timer5 = 0l;


    /** Textures **/
    private Texture bobTexture;
    private Texture bobTexture2;
    private Texture blockTexture;

    private SpriteBatch spriteBatch;
    private boolean debug = true;
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

    private Array<Rectangle> tiles;

    private Pool<Rectangle> rectPool = new Pool<Rectangle>() {
        @Override
        protected Rectangle newObject () {
            return new Rectangle();
        }
    };

    public WorldRenderer(World world, GameScreen gameScreen) {
        this.gameScreen = gameScreen;

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
        Gdx.input.setInputProcessor(this);
        tiles = new Array<Rectangle>();
        tiles = world.getTile();

        player.setPosition(new Vector2(1, Ground.SIZE));
        player.setWidth(UNIT_SCALE * playerIdleRight.getRegionWidth());
        player.setHeight(UNIT_SCALE * playerIdleRight.getRegionHeight());

    }

    private void loadTextures() {
        bobTexture = new  Texture(Gdx.files.internal("images/rect.jpg"));
        bobTexture2 = new  Texture(Gdx.files.internal("images/rect.jpg"));
     //   blockTexture = new Texture(Gdx.files.internal("images/block.png"));
    }


    public void render(float delta) {

        obstacleSelect = random.nextInt(Obstacle.numberObstale)+1;
      //  System.out.println("obstacle number " + obstacleSelect);

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

        // show the game over screen
        if (collide){
            gameScreen.setSate(GameScreen.GAME_OVER);
        }

        collisionDetection();
        spriteBatch.begin();
        //drawBlocks();
        drawBob();
        drawPlayer();
        spriteBatch.end();
        updateObstable();
        updatePlayer(delta);
        player.update(delta);

        if (debug)
            drawDebug();
    }




    public void updatePlayer(float delta) {
        if (delta == 0) return;

        if(collide == true){
            player.setVelocity(new Vector2(0,0));
            return;
        }

        if(player.getState() != Player.State.Falling && player.getState() != Player.State.Standing){
            if(player.getVelocity().y < 0){
                player.setState(Player.State.Falling);
                player.setGrounded(false);

            }
        }

        player.getAcceleration().y = Player.GRAVITY;
        player.getAcceleration().scl(delta);
        player.getVelocity().add(player.getAcceleration().x, player.getAcceleration().y);

        // clamp the velocity to the maximum, x-axis only
        if (Math.abs(player.getVelocity().x) > Player.MAX_VELOCITY) {
            player.getVelocity().x = Math.signum(player.getVelocity().x) * Player.MAX_VELOCITY;
        }

        // clamp the velocity to 0 if it's < 1, and set the state to standing
        if (Math.abs(player.getVelocity().x) < 1) {
            player.getVelocity().x = 0;
            if (player.isGrounded()) {
                player.setState(Player.State.Standing);
            }
        }

        player.getVelocity().scl(delta);

        // perform collision detection & response, on each axis, separately
        // if the koala is moving right, check the tiles to the right of it's
        // right bounding box edge, otherwise check the ones to the left
        Rectangle playerRect = rectPool.obtain();
        playerRect.set(player.getPosition().x, player.getPosition().y + player.getHeight()*0.1f, player.getWidth(), player.getHeight());

        int startX, startY, endX, endY;
        if (player.getVelocity().x > 0) {
            startX = endX = (int)(player.getPosition().x + player.getWidth() + player.getVelocity().x);
        } else {
            startX = endX = (int)(player.getPosition().x + player.getVelocity().x);
        }

        startY = (int)(player.getPosition().y);
        endY = (int)(player.getPosition().y + player.getHeight());
        getTiles(startX, startY, endX, endY, tiles);

        playerRect.x += player.getVelocity().x;

        if(tiles != null) {
            for (Rectangle tile : tiles) {

                if (playerRect.overlaps(tile)) {

                    if (player.getVelocity().x > 0) {
                        player.getPosition().x = tile.x - TILEWIDTH - TILEWIDTH * 0.40f;
                    } else if (player.getVelocity().x < 0) {
                        player.getPosition().x = tile.x + TILEWIDTH + TILEWIDTH * 0.05f;
                    }

                    player.getVelocity().x = 0;

                    break;
                }
            }
        }

        //playerRect.x = player.getPosition().x;
        playerRect.set(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());

        // if the koala is moving upwards, check the tiles to the top of it's
        // top bounding box edge, otherwise check the ones to the bottom
        if (player.getVelocity().y > 0) {
            startY = endY = (int)(player.getPosition().y + player.getHeight() + player.getVelocity().y);
        } else {
            startY = endY = (int)(player.getPosition().y + player.getVelocity().y);
        }

        startX = (int)(player.getPosition().x);
        endX = (int)(player.getPosition().x + player.getWidth());
        getTiles(startX, startY, endX, endY, tiles);
        playerRect.y += player.getVelocity().y;
        if(tiles != null) {
            for (Rectangle tile : tiles) {
                if (playerRect.overlaps(tile)) {
                    // we actually reset the koala y-position here
                    // so it is just below/above the tile we collided with
                    // this removes bouncing :)
                    if (player.getVelocity().y > 0) {
                        player.getVelocity().y = tile.y - player.getHeight();
                        // we hit a block jumping upwards, let's destroy it!
                        //					TiledMapTileLayer layer = (TiledMapTileLayer)level.getMap().getLayers().get(1);
                        //					layer.setCell((int)tile.x, (int)tile.y, null);
                    } else {
                        player.getPosition().y = tile.y + tile.height;
                        // if we hit the ground, mark us as grounded so we can jump
                        player.setGrounded(true);
                    }
                    player.getVelocity().y = 0;
                    break;
                }
            }
        }
        rectPool.free(playerRect);

        // unscale the velocity by the inverse delta time and set
        // the latest position

        player.getPosition().add(player.getVelocity());

        player.getVelocity().scl(1 / delta);

        player.getVelocity().x *= Player.DAMPING;
    }

    private void getTiles (int startX, int startY, int endX, int endY, Array<Rectangle> tiles) {

    }


    // Modifie la position de l'obstacle, sa vitesse et tourne en boucle
    public void updateObstable(){

        if ( !(obstacle.getPosition().x < 0 && collide)) {
            if (obstacle.getPosition().x < 0) {
                obstacle.getPosition().x = OUT_RANGE_X;
                obstacle2.getPosition().x = OUT_RANGE_X;
                finishTour = true;
            }
            else {
                 if (timer == SPEED_OBSTACLE) {
                if (collide != true) {
                    obstacle.getPosition().x = (float) obstacle.getPosition().x - (float) MOVING_RANGE;
                    obstacle.getBounds().x = (float) obstacle.getPosition().x - (float) MOVING_RANGE;
                    obstacle2.getPosition().x = (float) obstacle2.getPosition().x - (float) MOVING_RANGE;
                    obstacle2.getBounds().x = (float) obstacle2.getPosition().x - (float) MOVING_RANGE;
                    timer = 0;
                    finishTour = false;
                }
            }
            }
            timer++;
        }


    }

    public void collisionDetection(){
        //System.out.println(player.getPosition().x);

        float topObstacle1 = (obstacle.getPosition().x) + (Obstacle.SIZEWIDTH);

        //System.out.println(topObstacle1);
        float bottomObstacle2 = (obstacle2.getPosition().y);
        //System.out.println( "player " +player.getPosition().y);
        //System.out.println("obstacle 2 " +bottomObstacle2);
        //System.out.println("obstacle " + topObstacle1);
        //System.out.println(collide);
        //System.out.println();
        /*if ( !(((player.getPosition().y)> (topObstacle1)) && ((player.getPosition().y) < (bottomObstacle2) ) )) {
            if ((player.getPosition().x >(obstacle.getPosition().x - com.mygdx.game.model.Ground.SIZE) )) {
                if (player.isGrounded()) {
                    player.setPosition(new Vector2((float) (obstacle.getPosition().x - com.mygdx.game.model.Ground.SIZE), (float) (player.getPosition().y)));
                    collide = true;
                }
                //  System.out.println("enter collision " );
            }
        }*/

        if ((player.getPosition().x >= (obstacle.getPosition().x - player.getWidth())
                && player.getPosition().x < (obstacle.getPosition().x + Obstacle.SIZEWIDTH)
                && player.getPosition().y >= obstacle.getPosition().y
                && (player.getPosition().y) < (obstacle.getBounds().getHeight() + obstacle.getPosition().y))

                || (player.getPosition().x >= (obstacle2.getPosition().x - player.getWidth())
                && player.getPosition().x <= (obstacle2.getPosition().x + obstacle2.getBounds().getWidth())
                && (player.getPosition().y + player.getHeight())>= obstacle2.getPosition().y)){

            player.setPosition(new Vector2((float) (obstacle.getPosition().x - player.getWidth()), (float) (player.getPosition().y)));
            collide = true;
            System.out.println("Joueur x= " + (player.getPosition().x + player.getWidth()) + "  y= " + player.getPosition().y);
            System.out.println("Cube x= " + obstacle.getPosition().x + "  y= " + obstacle.getPosition().y );

            if ((player.getPosition().x >= (obstacle2.getPosition().x - player.getWidth())
                    && player.getPosition().x <= (obstacle2.getPosition().x + Obstacle.SIZEWIDTH2)
                    && (player.getPosition().y + player.getHeight())>= obstacle2.getPosition().y)) System.out.println("collision2");

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
        //debugRenderer.end();

        //debugRenderer.begin(ShapeType.Line);

        Rectangle rect2 = new Rectangle(0, 0, player.getWidth(), player.getHeight());
        float xx1 = player.getPosition().x + rect2.x;
        float yy1 = player.getPosition().y + rect2.y;
        debugRenderer.setColor(new Color(1, 1, 1, 1));
        debugRenderer.rect(xx1, yy1, rect2.width, rect2.height);
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

        spriteBatch.draw(playerFrame, player.getPosition().x * ppuX, player.getPosition().y * ppuY, player.getWidth() * ppuX, player.getHeight() * ppuY);
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pressTime = System.currentTimeMillis();
        timer1 = pressTime + (MAX_TIME_PRESS/5);
        timer2 = timer1 + (MAX_TIME_PRESS/5);
        timer3 = timer2 + (MAX_TIME_PRESS/5);
        timer4 = timer3 + (MAX_TIME_PRESS/5);
        timer5 = timer4 + (MAX_TIME_PRESS/5);

        if (isClicked == false && player.isGrounded()){
            isClicked = true;
            jumpingPressed = true;
        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (player.isGrounded()){
            if (isClicked == true){
                releaseTime = System.currentTimeMillis();
                isClicked = false;
            }
            duration = releaseTime - pressTime;
            player.setGrounded(false);
            player.setState(Player.State.Jumping);

            if (jumpingPressed == true) {
                if (duration >= MAX_TIME_PRESS){
                    player.getVelocity().y = Player.MAX_JUMP_SPEED;
                } else if (duration <= MIN_TIME_PRESS) {
                    player.getVelocity().y = ((float)MIN_TIME_PRESS/MAX_TIME_PRESS) * Player.MAX_JUMP_SPEED;
                } else {
                    player.getVelocity().y = ((float)duration/MAX_TIME_PRESS) * Player.MAX_JUMP_SPEED;
                }
                jumpingPressed = false;
            }
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}