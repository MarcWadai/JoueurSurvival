package com.mygdx.game.android.view;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.android.model.Obstacle;

/**
 * Created by rxcai on 03/10/15.
 */
public class World {

    /** The blocks making up the world **/
    Array<com.mygdx.game.model.Ground> blocks = new Array();
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
        createDemoWorld();
    }

    private void createDemoWorld() {
        bob = new Obstacle(new Vector2(9, 1), 0, 0);

        for (int i = 0; i < 30; i++) {
            blocks.add(new com.mygdx.game.model.Ground(new Vector2(i, 0)));
        }

    }

}
