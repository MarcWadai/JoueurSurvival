package com.mygdx.game.controler;

/**
 * Created by thanh on 03/10/15.
 */


    import com.badlogic.gdx.Gdx;
    import com.badlogic.gdx.InputProcessor;
    import com.badlogic.gdx.graphics.GL20;
    import com.badlogic.gdx.graphics.OrthographicCamera;
    import com.badlogic.gdx.graphics.Texture;
    import com.badlogic.gdx.graphics.g2d.SpriteBatch;
    import com.badlogic.gdx.graphics.g2d.TextureAtlas;
    import com.badlogic.gdx.graphics.g2d.TextureRegion;
    import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
    import com.badlogic.gdx.math.Rectangle;
    import com.badlogic.gdx.math.Vector2;
    import com.badlogic.gdx.utils.Array;
    import com.badlogic.gdx.utils.Pool;
    import com.mygdx.game.Assets;
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
    private static final float MOVING_RANGE = 0.085f;//0.07f;
    private final static  float OUT_RANGE_X = 9;
    private static final float UNIT_SCALE = 1/400f;
    private static final long MAX_TIME_PRESS = 1000;
    private static final long MIN_TIME_PRESS = 200;
    private static final long MAX_TIME_JUMP = 2000;
    private static final long TILEWIDTH = 1;
    private SpriteBatch spriteBatchObstacle;
    float timer = 0;

    GameScreen gameScreen;


    private World world;
    private Player player;
    private OrthographicCamera cam;

    /**  Boolean and variables to change the background based on the game mode **/
    private int minTimerFly = 1;
    private int maxTimerFly = 1;
    private int timerFly = 0;
    private int counterFly = 0;
    private int counterInFly = 0;
    private int timerInterGroundFly = 10;
    private boolean flyBackgroundEnter = false;


    /** Obstacle and collision variables**/
    private Obstacle obstacle;
    private Obstacle obstacle2;
    private Boolean collide = false;
    Random random;
    int obstacleSelect;
    boolean finishTour = true;
    int score = 0 ;


    /** for debug rendering **/
    ShapeRenderer debugRenderer = new ShapeRenderer();

    /** Player textures **/
    private TextureRegion playerIdleLeft;
    private TextureRegion playerIdleRight;
    private TextureRegion playerJumpLeft;
    private TextureRegion playerJumpRight;
    private TextureRegion playerFrame;
    private TextureRegion playerFall;
    private TextureRegion playerFall1;
    private TextureRegion playerFall2;
    private TextureRegion playerJump1;
    private TextureRegion playerJump2;
    private TextureRegion playerJump3;
    private TextureRegion playerCrying;

    /** Jumping variables**/
    private boolean isClicked = false;
    private long pressTime = 0l;
    private long currentTime = 0l;
    private long releaseTime = 0l;
    private long duration = 0l;
    private boolean jumpingPressed;
    private long timer1 = 0l;
    private long timer2 = 0l;
    private long timer3 = 0l;

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
        obstacle2Y = obstacle.getPosition().y +Obstacle.SIZEHEIGHT;
        obstacle2 = new Obstacle(new Vector2((float)10,obstacle2Y),Obstacle.SIZEWIDTH, Obstacle.SIZEHEIGHT);
        debug = true;
        spriteBatch = new SpriteBatch();
        spriteBatchObstacle = new SpriteBatch();
        player = new Player();
        loadPlayerTextures();
        Gdx.input.setInputProcessor(this);
        tiles = new Array<Rectangle>();
        tiles = world.getTile();

        player.setPosition(new Vector2(1, Ground.SIZE));
        player.setWidth(UNIT_SCALE * playerIdleRight.getRegionWidth());
        player.setHeight(UNIT_SCALE * playerIdleRight.getRegionHeight());

        score = 0 ;

        // set the first timer for the change of mode
       // timerFly = random.nextInt(maxTimerFly) + minTimerFly;
        timerFly = 2;
    }

    public void render(float delta) {

        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        obstacleSelect = 4;
        //obstacleSelect = random.nextInt(Obstacle.numberObstale)+1;
      //  System.out.println("obstacle number " + obstacleSelect);

        if ( finishTour) {
            switch (obstacleSelect){
                case 1 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE11_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y + Obstacle.OBSTACLE11_HEIGHT);
                    break;
                case 2 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE12_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y +Obstacle.OBSTACLE12_HEIGHT +  Obstacle.HOLE);
                    break;
                case 3 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE13_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y + obstacle.getBounds().height + Obstacle.HOLE);
                    obstacle2.setHeight(Obstacle.OBSTACLE17_HEIGHT);
                    break;

                case 4 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle2.setHeight(Obstacle.OBSTACLE17_HEIGHT);
                    obstacle.setHeight(Obstacle.OBSTACLE14_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y +Obstacle.OBSTACLE14_HEIGHT +  Obstacle.HOLE);
                    break;

                case 5 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE15_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y + Obstacle.OBSTACLE15_HEIGHT + Obstacle.HOLE);
                    break;
                case 6 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE16_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y +Obstacle.OBSTACLE16_HEIGHT+  Obstacle.HOLE);
                    break;
                case 7 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE17_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y +Obstacle.OBSTACLE17_HEIGHT+  Obstacle.HOLE);
                    break;
                case 8 :
                    obstacle.getPosition().x = OUT_RANGE_X;
                    obstacle2.getPosition().x = OUT_RANGE_X;
                    obstacle.setHeight(Obstacle.OBSTACLE11_HEIGHT);
                    obstacle2.setY(obstacle.getPosition().y +Obstacle.OBSTACLE17_HEIGHT +  3*Obstacle.HOLE );
                    break;
            }
            score++;
        }

        // show the game over screen
        if (collide){
            gameScreen.setSate(GameScreen.GAME_OVER);
        }
        collisionDetection();

        spriteBatchObstacle.begin();
        spriteBatch.begin();
        updateObstable();
        if (flyBackgroundEnter){
            drawBackground(Assets.background);
            if (counterInFly == timerFly){
                flyBackgroundEnter = false;
                counterInFly = 0;
                counterFly = 0;
                //timerFly = random.nextInt(maxTimerFly) + minTimerFly;
                timerFly = 2;
            }
        } else {
            drawBackground(Assets.background2tr);
        }

        drawPlayer();
        drawBob();
        spriteBatch.end();
        spriteBatchObstacle.end();
        updatePlayer(delta);
        player.update(delta);

        //if (debug)
        //    drawDebug();

        /* Touch down animation */
        currentTime = System.currentTimeMillis();
        if (Gdx.input.isTouched() && player.isGrounded()) {
            if (currentTime < timer1){
                player.setState(Player.State.Falling);
            } else if (currentTime >= timer1 && currentTime < timer2){
                player.setState(Player.State.Falling1);
            } else if (currentTime >= timer2 && currentTime < timer3){
                player.setState(Player.State.Falling2);
            }
        }

        /* Touch up animation */
        if (!player.isGrounded() && player.getState() == Player.State.Jumping) {
            if (currentTime >= timer3 && currentTime < (timer3 + (MAX_TIME_JUMP/4))) {
                player.setState(Player.State.Jumping);
            } else if (currentTime >= (timer3 + (MAX_TIME_PRESS/4)) && currentTime < (timer3 + 2*(MAX_TIME_JUMP/4))) {
                player.setState(Player.State.Jumping3);
            } else if (currentTime >= (timer3 + 2*(MAX_TIME_PRESS/4)) && currentTime < (timer3 + 3*(MAX_TIME_JUMP/4))) {
                player.setState(Player.State.Jumping3);
            } else if (currentTime >= (timer3 + 3*(MAX_TIME_PRESS/4)) && currentTime < (timer3 + MAX_TIME_JUMP)) {
                player.setState(Player.State.Jumping3);
            }
        }

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
            if (player.isGrounded() && !Gdx.input.isTouched()) {
                player.setState(Player.State.Standing);
            }
        }

        player.getVelocity().scl(delta);

        // perform collision detection & response, on each axis, separately
        // if the koala is moving right, check the tiles to the right of it's
        // right bounding box edge, otherwise check the ones to the left
        Rectangle playerRect = rectPool.obtain();
        playerRect.set(player.getPosition().x, player.getPosition().y + player.getHeight()*0.1f, player.getWidth(), player.getHeight());

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

        playerRect.set(player.getPosition().x, player.getPosition().y, player.getWidth(), player.getHeight());

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

    // Modifie la position de l'obstacle, sa vitesse et tourne en boucle et le background
    public void updateObstable(){

        if ( !(obstacle.getPosition().x < 0 && collide)) {
            if (obstacle.getPosition().x < 0) {
                obstacle.getPosition().x = OUT_RANGE_X;
                obstacle2.getPosition().x = OUT_RANGE_X;
                finishTour = true;
                counterFly ++;

                // si le counterfly atteint la valeur de timerfly on pass le booleen a vrai et on va changer le background pendant une certaine durée
                if (counterFly == timerFly){
                    flyBackgroundEnter = true;
                }
                if (flyBackgroundEnter){
                    counterInFly ++;
                    System.out.println("in fly bacground, timerFly :  " + timerFly +", counterinfly :" +minTimerFly );
                }

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

        if ((player.getPosition().x >= (obstacle.getPosition().x - player.getWidth())
                && player.getPosition().x < (obstacle.getPosition().x + Obstacle.SIZEWIDTH)
                && player.getPosition().y >= obstacle.getPosition().y
                && (player.getPosition().y) < (obstacle.getBounds().getHeight() + obstacle.getPosition().y))

                || (player.getPosition().x >= (obstacle2.getPosition().x - player.getWidth())
                && player.getPosition().x <= (obstacle2.getPosition().x + obstacle2.getBounds().getWidth())
                && (player.getPosition().y + player.getHeight())>= obstacle2.getPosition().y)){

            player.setPosition(new Vector2((float) (player.getPosition().x), (float) (player.getPosition().y)));
            player.setState(Player.State.Crying);
            collide = true;
        }
        else {
            collide = false;
        }
    }

    private void drawBackground(TextureRegion background) {
        spriteBatch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

   private void drawBob() {

        switch (obstacleSelect) {
            case 1 :
                spriteBatch.draw(Assets.obstacleBackground11, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);// obstacle.getBounds().width, obstacle.getBounds().height);
                //spriteBatch.draw(Assets.obstacleBackground17, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, obstacle2.getBounds().width, obstacle2.getBounds().height);
                break;
            case 2 :
                spriteBatch.draw(Assets.obstacleBackground12, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);
             //   spriteBatch.draw(Assets.obstacleBackground17, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, obstacle2.getBounds().width, obstacle2.getBounds().height);
                break;
            case 3 :
                spriteBatch.draw(Assets.obstacleBackground13, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);
                /*debugRenderer.begin(ShapeRenderer.ShapeType.Line);
                debugRenderer.rect(obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);
                debugRenderer.end();*/
             //  System.out.println("papaer width : " + paperTextureWidth +" height : " + paperTextureHeight);
                break;
            case 4 :
                spriteBatch.draw(Assets.obstacleBackground14, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);
                spriteBatch.draw(Assets.obstacleBackground17, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, obstacle2.getBounds().width * ppuX, obstacle2.getBounds().height * ppuY);
                break;
            case 5 :
                spriteBatch.draw(Assets.obstacleBackground15, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);
                //spriteBatch.draw(Assets.obstacleBackground17, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, obstacle2.getBounds().width, obstacle2.getBounds().height);
                break;
            case 8:
                spriteBatch.draw(Assets.obstacleBackground16, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width, obstacle.getBounds().height);
                //spriteBatch.draw(Assets.obstacleBackground17, obstacle2.getPosition().x * ppuX, obstacle2.getPosition().y * ppuY, obstacle2.getBounds().width,obstacle2.getBounds().height);
                break;
        }
        //spriteBatch.draw(Assets.obstacleBackground16, obstacle.getPosition().x * ppuX, obstacle.getPosition().y * ppuY, obstacle.getBounds().width * ppuX, obstacle.getBounds().height * ppuY);// obstacle.getBounds().width, obstacle.getBounds().height);

    }


    public void loadPlayerTextures(){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("poop.pack"));
        /* Standing */
        playerIdleLeft = atlas.findRegion("1");

        playerIdleRight = new TextureRegion(playerIdleLeft);
        playerIdleRight.flip(true, false);

        /* Jumping */
        playerJumpLeft = atlas.findRegion("5");
        playerJumpRight = new TextureRegion(playerJumpLeft);
        playerJumpRight.flip(true, false);
        playerJump1 = atlas.findRegion("6");
        playerJump2 = atlas.findRegion("7");
        playerJump3 = atlas.findRegion("8");

        /* Falling */
        playerFall = atlas.findRegion("2");
        playerFall1 = atlas.findRegion("3");
        playerFall2 = atlas.findRegion("4");

        /* Crying */
        playerCrying = atlas.findRegion("9");

    }


    public void drawPlayer(){

        playerFrame = playerIdleRight;

        if (player.getState() == Player.State.Jumping) {

            playerFrame = player.isFacingRight() ? playerJumpRight : playerJumpLeft;

        } else if (player.getState() == Player.State.Falling) {

            playerFrame = playerFall;

        } else if (player.getState() == Player.State.Standing) {

            playerFrame = player.isFacingRight() ? playerIdleRight : playerIdleLeft;

        } else if (player.getState() == Player.State.Falling1) {

            playerFrame = playerFall1;

        } else if (player.getState() == Player.State.Falling2) {

            playerFrame = playerFall2;

        } else if (player.getState() == Player.State.Jumping1) {

            playerFrame = playerJump1;

        } else if (player.getState() == Player.State.Jumping2) {

            playerFrame = playerJump2;

        } else if (player.getState() == Player.State.Jumping3) {

            playerFrame = playerJump3;

        } else if (player.getState() == Player.State.Crying) {

            playerFrame = playerCrying;

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
        if (gameScreen.getPauseBounds().contains(screenX, screenY)) {
            gameScreen.setSate(gameScreen.GAME_PAUSED);
            System.out.println("Game PAUSE");
        }
        else if (gameScreen.getState() == gameScreen.GAME_OVER && gameScreen.getPauseMenuBounds().contains(screenX,screenY)){
            gameScreen.setSate(gameScreen.GAME_RESTART);
            System.out.println("Game RESTART");
        }
        else if (gameScreen.getState() == gameScreen.GAME_PAUSED && gameScreen.getPauseMenuBounds().contains(screenX,screenY)){
            gameScreen.setSate(GameScreen.GAME_RUNNING);
        }
        else {
            pressTime = System.currentTimeMillis();
            timer1 = pressTime + (MAX_TIME_PRESS / 3);
            timer2 = timer1 + (MAX_TIME_PRESS / 3);
            timer3 = timer2 + (MAX_TIME_PRESS / 3);
            //timer4 = timer3 + (MAX_TIME_PRESS / 5);
            //timer5 = timer4 + (MAX_TIME_PRESS / 5);

            if (isClicked == false && player.isGrounded()) {
                isClicked = true;
                jumpingPressed = true;
            }
        }
        System.out.println("x:"+screenX+", y:"+screenY);
        System.out.println("xbound:" + gameScreen.getPauseMenuBounds().x + ", ybound:" + gameScreen.getPauseMenuBounds().y);


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

    public float getppux(){
        return ppuX;
    }

    public float getppuy(){
        return ppuY;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

}