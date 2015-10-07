package com.mygdx.game.view;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.model.Ground;
import com.mygdx.game.model.Obstacle;

/**
 * Created by rxcai on 03/10/15.
 */
public class World {

    /** The blocks making up the world **/
    Array<com.mygdx.game.model.Ground> blocks = new Array();
    private Array<Rectangle> tile;
    /** Our player controlled hero **/
    Obstacle bob;

    // Getters -----------
    public Array<com.mygdx.game.model.Ground> getBlocks() {
        return blocks;
    }
    public Obstacle getBob() {
        return bob;
    }
    // --------------------

    public World() {
        tile = new Array<Rectangle>();
        createDemoWorld();
    }

    private void createDemoWorld() {
        bob = new Obstacle(new Vector2(9, 1), 0, 0);
        for (int i = 0; i < 30; i++) {
            blocks.add(new com.mygdx.game.model.Ground(new Vector2(i, 0)));
            tile.add(new Rectangle((float) i, (float)0, Ground.SIZE, Ground.SIZE ));
        }


    }

    public Array<Rectangle> getTile(){
        return this.tile;
    }

}
