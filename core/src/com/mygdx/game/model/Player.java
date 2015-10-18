package com.mygdx.game.model;

import com.badlogic.gdx.math.Vector2;

/**
 * Created by TRINH on 02/10/15.
 */
public class Player {
    public static final float MAX_VELOCITY = 20f; //1
    public static final float JUMP_VELOCITY = 20f;
    public static final float DAMPING = 0.87f;
    public static final float GRAVITY = -5.0f; //-5      g13 s10 depasse pas
    public static final float MAX_JUMP_SPEED   = 6.2f;//10

    public enum State {
        Standing, Walking, Jumping, Jumping1, Jumping2, Jumping3, Falling, Falling1, Falling2, Crying
    }

    private State state;
    private boolean facingRight;
    private boolean grounded;
    private float stateTime;
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float width;
    private float height;

    public Player(){
        position = new Vector2();
        velocity = new Vector2();
        acceleration = new Vector2();
        state = State.Standing;
        facingRight = true;
        stateTime = 0;
        grounded = true;
    }

    public Player(Vector2 v2, float width, float height){
        this.position = v2;
        this.width = width;
        this.height = height;
    }

    /**************************************************** Getters/Setters *******************************************************/

    public Vector2 getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(Vector2 acceleration) {
        this.acceleration = acceleration;
    }

    public void update(float delta){
        stateTime += delta;
        position.add(velocity.cpy().scl(delta));
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public boolean isFacingRight() {
        return facingRight;
    }

    public void setFacingRight(boolean facingRight) {
        this.facingRight = facingRight;
    }

    public boolean isGrounded() {
        return grounded;
    }

    public void setGrounded(boolean grounded) {
        this.grounded = grounded;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector2 velocity) {
        this.velocity = velocity;
    }
}
