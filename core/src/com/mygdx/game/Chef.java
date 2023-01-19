package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class Chef extends Sprite {

    SpriteBatch spriteBatch;
    private float stateTime;

    public enum State {WALKING, STANDING}
    State currentState;
    String prevState;
    TextureRegion standingTexture;
    Animation<TextureRegion> walkAnimation;
    private Level level;
    private float walkingSpeed;
    private float runningSpeed;
    private Texture chefTexture;
    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 5;

    @Override
    public void draw(Batch batch) {
        //super.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame;

        if (isStanding()){
            currentFrame = standingTexture;
        } else {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        }
        handleInput();
        batch.draw(currentFrame, getX(), getY(), 32, 32);
    }


    public Chef(Level level){
        this.level = level;
        walkingSpeed = 5;
        currentState = State.STANDING;

        chefTexture = new Texture(Gdx.files.internal("chef/a1.png"));

        // Load chef walk animation
        TextureRegion[][] tmp = TextureRegion.split(chefTexture,
                chefTexture.getWidth() / FRAME_COLS,
                chefTexture.getHeight() / FRAME_ROWS);
        TextureRegion[] frames = new TextureRegion[FRAME_COLS];
        int index = 0;
        for (int i = 0; i < FRAME_COLS; i++) {
            frames[index++] = tmp[1][i];
        }

        walkAnimation = new Animation<TextureRegion>(0.25f, frames);
        standingTexture = new TextureRegion(frames[0]);
        //spriteBatch = new SpriteBatch();
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
    }




    
}
