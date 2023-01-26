package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class Chef extends Sprite implements InputProcessor {

    SpriteBatch spriteBatch;
    private float stateTime;
    private Vector2 velocity = new Vector2();

    public enum State {WALKING, STANDING}
    public enum Direction {LEFT, RIGHT}
    State currentState;
    Direction currentDirection;
    Direction previousDirection = Direction.RIGHT;
    public boolean flipChef = false;
    String prevState;
    Animation<TextureRegion> walkAnimation;
    Animation<TextureRegion> idleAnimation;
    TiledMapTileLayer collisionLayer;
    private float walkingSpeed;
    private float runningSpeed;

    private TextureRegion[][] allTiles;
    private static final int BASE_WIDTH = 12;
    private static final int BASE_HEIGHT = 18;
    private static final float RENDERED_WIDTH = (float) (BASE_WIDTH * 1.5);
    private static final float RENDERED_HEIGHT = (float) (BASE_HEIGHT * 1.5);
    boolean collideX, collideY;

    @Override
    public void draw(Batch batch) {
        //super.draw(batch);
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame;
        if (isStanding()){
            currentFrame = idleAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        }
        update(Gdx.graphics.getDeltaTime());
        batch.draw(currentFrame, flipChef ? getX() + RENDERED_WIDTH : getX(), getY(), flipChef ? -RENDERED_WIDTH : RENDERED_WIDTH, RENDERED_HEIGHT);
    }

    public Chef(Level level){
        walkingSpeed = 120;
        currentState = State.STANDING;
        collisionLayer = level.getMapTileLayer(1);
        collideX=false;
        collideY=false;
        Texture chefSheet = new Texture("chef/a1_new.png");

        // Load all chef sprites in our new custom sprite sheet.
        // In this format, each sprite is a 12x18 region. It can be expanded to add more sprites.
        // 0 - Idles
        // 1 - Walks
        allTiles = TextureRegion.split(chefSheet,12,18);

        TextureRegion[] idleFrames = fill_frames(0, 3);
        TextureRegion[] walkFrames = fill_frames(1, 5);
        idleAnimation = new Animation<>(0.35f, idleFrames);
        walkAnimation = new Animation<>(0.15f, walkFrames);

        setSize(BASE_WIDTH, BASE_HEIGHT);
    }

    private TextureRegion[] fill_frames(int row, int count){
        TextureRegion[] frames = new TextureRegion[count];
        int index = 0;
        for (int i = 0; i < frames.length; i++) {
            frames[index++] = allTiles[row][i];
        }
        return frames;
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
        for (float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
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

        if (playerLocY < 0 || playerLocY > Level.MAP_HEIGHT -1 || playerLocX < 0 || playerLocX > Level.MAP_WIDTH){
            return true;
        }

        if (collisionLayer.getCell(playerLocX, playerLocY) != null){
            System.out.println(collisionLayer.getCell(playerLocX, playerLocY).getTile().getProperties().containsKey("solid"));
            return true;
        }
        return false;
    }

    public boolean isStanding(){
        return (
            currentState == State.STANDING
            && velocity.x == 0
            && velocity.y == 0
        );
    }

    @Override
    public boolean keyDown(int keycode) {
        flipChef = false;
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
                currentDirection = Direction.LEFT;
                velocity.x -= walkingSpeed;
                break;
            case Input.Keys.D:
                currentState = State.WALKING;
                currentDirection = Direction.RIGHT;
                velocity.x += walkingSpeed;
                break;
        }
        if (currentDirection == Direction.LEFT) {
            flipChef = true;
        }
        previousDirection = currentDirection;

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
                velocity.x = 0;
                currentState = State.STANDING;
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
