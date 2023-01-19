package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Chef extends Sprite implements InputProcessor {

    SpriteBatch spriteBatch;
    private float stateTime;
    private Vector2 velocity=new Vector2();

    public enum State {WALKING, STANDING}
    State currentState;
    String prevState;
    TextureRegion standingTexture;
    Animation<TextureRegion> walkAnimation;
    TiledMapTileLayer collisionLayer;
    private Level level;
    private float walkingSpeed;
    private float runningSpeed;
    private Texture chefTexture;
    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 5;
    boolean collideX, collideY;

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
        update(Gdx.graphics.getDeltaTime());
        batch.draw(currentFrame, getX(), getY(), 32, 32);

    }


    public Chef(Level level){
        this.level = level;
        walkingSpeed = 120;
        currentState = State.STANDING;
        collisionLayer = level.getMapTileLayer(1);
        collideX=false;
        collideY=false;
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

        setSize(chefTexture.getWidth() / FRAME_COLS, chefTexture.getHeight() / FRAME_ROWS);
        walkAnimation = new Animation<TextureRegion>(0.25f, frames);
        standingTexture = new TextureRegion(frames[0]);


        //spriteBatch = new SpriteBatch();
    }

    public void update(float delta){

        float oldX = getX(), oldY = getY();
        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();

        boolean blocked = isCellBlocked(getX(), getY() + getHeight());


        setX(getX() + velocity.x * delta);
        if (velocity.x < 0){
            collideX = collidesLeft();
        } else if (velocity.x > 0) {
            collideX = collidesRight();
        } else {
            collideX = false;
        }

        if (collideX) {
            setX(oldX);
            System.out.println("collidedX");
            velocity.x = 0;
        }

        setY(getY() + velocity.y * delta);

        if (velocity.y < 0){
            collideY = collidesBottom();
        } else if (velocity.y > 0) {
            collideY = collidesTop();
        }

        if (collideY) {
            setY(oldY);
            System.out.println("collidedY");
            velocity.y = 0;
        }

    }

    private boolean isCellBlocked(float x, float y){
        TiledMapTileLayer.Cell cell = collisionLayer.getCell((int) (x / collisionLayer.getTileWidth()), (int) (y / collisionLayer.getTileHeight()));
        return cell != null && cell.getTile().getProperties().containsKey("solid");
    }
    private boolean collidesLeft() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if (isCellBlocked(getX(), getY() + step))
                return true;
        return false;
    }
    private boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if (isCellBlocked(getX() + getWidth(), getY() + step))
                return true;
        return false;
    }

    private boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if (isCellBlocked(getX() + step, getY() + getHeight()/2))
                return true;
        return false;
    }

    private boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2)
            if (isCellBlocked(getX() + step, getY() + getHeight()/2))
                return true;
        return false;
    }

    private boolean collides(){

        //System.out.println(layer.getCell(0,0));

        int playerLocX = (int)(getX()/collisionLayer.getTileWidth());
        int playerLocY = (int)(getY()/collisionLayer.getTileHeight());

        if (playerLocY < 0 || playerLocY > level.MAP_HEIGHT-1 || playerLocX < 0 || playerLocX > level.MAP_WIDTH){
            return true;
        }
        if (collisionLayer.getCell(playerLocX, playerLocY) != null){
            System.out.println(collisionLayer.getCell(playerLocX, playerLocY).getTile().getProperties().containsKey("solid"));
            return true;
        }
        return false;
    }


    public boolean isStanding(){
        if (currentState == State.STANDING){
            return true;
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.W:
                velocity.y += walkingSpeed;
                currentState = State.WALKING;
                break;
            case Input.Keys.S:
                currentState = State.WALKING;
                velocity.y -= walkingSpeed;
                break;
            case Input.Keys.A:
                currentState = State.WALKING;
                velocity.x -= walkingSpeed;
                break;
            case Input.Keys.D:
                currentState = State.WALKING;
                velocity.x += walkingSpeed;
                break;

        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
            case Input.Keys.S:
                velocity.y = 0;
                currentState = State.STANDING;
                break;
            case Input.Keys.A:
            case Input.Keys.D:
                currentState = State.STANDING;
                velocity.x = 0;
                break;

        }

        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
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
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
