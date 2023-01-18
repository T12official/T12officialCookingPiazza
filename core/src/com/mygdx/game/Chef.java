package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Chef extends Sprite {
    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 5;

    SpriteBatch spriteBatch;
    private final float stateTime;

    public enum State {WALKING, STANDING};
    State currentState;
    String prevState;

    private Texture texture;
    TextureRegion chefStanding;
    Animation<TextureRegion> playerWalk;
    private Level level;
    private float walkingSpeed;
    private float runningSpeed;


    public Chef(Level level){

        walkingSpeed = 5;
        texture = new Texture(Gdx.files.internal("chef/a1.png"));
        TextureRegion[][] tmp = TextureRegion.split(texture,
                texture.getWidth() / FRAME_COLS,
                texture.getHeight() / FRAME_ROWS);

        chefStanding = tmp[0][0];
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
                frames[index++] = tmp[1][i];
        }
        currentState = State.WALKING;
        playerWalk = new Animation<TextureRegion>(0.25f, frames);
        stateTime = 0f;
        spriteBatch = new SpriteBatch();
    }

    public void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            this.setY(this.getY() + walkingSpeed);
            currentState = State.WALKING;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S)){
            this.setY(this.getY() - walkingSpeed);
            currentState = State.WALKING;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            this.setX(this.getX() + walkingSpeed);
            currentState = State.WALKING;
        }
        else if(Gdx.input.isKeyPressed(Input.Keys.A)){
            this.setX(this.getX() - walkingSpeed);
            currentState = State.WALKING;
        }
        else currentState = State.STANDING;

    }

    public boolean isStanding(){
        if (currentState == State.STANDING){
            return true;
        }
        return false;
    }

    public void dispose (){
        spriteBatch.dispose();
        texture.dispose();
    }




    
}
