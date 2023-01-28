package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private Dish HoldingDish = new Dish("new Dish");

    Level level;
    Level myLev;
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
        myLev = level;
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
            //System.out.println("collidedY");
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
            case (Input.Keys.E ):

                double minDist = 10000;
                int minIndex = 0;
                Map<String, List<Double>> data = new HashMap<String, List<Double>>();
                data = myLev.getSpriteData();
                Iterator<String> a = data.keySet().iterator();
                String shorttest = "";
                String temp;
                List<Double> x_y;
                while(a.hasNext()){
                   temp = a.next();
                   System.out.println(temp);
                   x_y = data.get(temp);
                    double currentDist =  Math.sqrt(   Math.pow ((x_y.get(0) - getX()), 2 ) + Math.pow( (x_y.get(1) - getY()) , 2) );
                    if (currentDist < minDist){
                        minDist = currentDist;
                        shorttest = temp;
                    }
                }
                if (minDist < 30) {
                    System.out.println("you are interacting with:" + shorttest);
                    performInteract(shorttest);
                }
                else {
                    System.out.println("to far to interact with cloest: " + shorttest);
                }

        }
        if (currentDirection == Direction.LEFT) {
            flipChef = true;
        }
        previousDirection = currentDirection;

        return true;
    }



    private void performInteract(String station){
        switch (station){
            case "tenderStation":
                break;
            case "cutStation":
                if (HoldingDish.getCurrentIngridients().get(0).getType() == Ingredient.Type.RAW_TOMATO){
                    //TODO implement delay on the cutting

                    HoldingDish.getCurrentIngridients().get(0).setType(Ingredient.Type.CHOPPED_TOMATO);
                }
                if (HoldingDish.getCurrentIngridients().get(0).getType() == Ingredient.Type.BUN){
                    System.out.println("cutting no2");
                    HoldingDish.getCurrentIngridients().get(0).setType(Ingredient.Type.CHOPPED_BUN);

                }

                break;
            case "cookStation":
                if (HoldingDish.getCurrentIngridients().size() == 0){
                    if (myLev.fryingOnOvenIngridient.getType() == Ingredient.Type.COOKED_BURGER){
                        System.out.println("grabbing");
                        HoldingDish.addIngridientClass(myLev.fryingOnOvenIngridient);
                        myLev.fryingOnOvenIngridient = null;
                        myLev.trackWithChef = HoldingDish;
                    }
                }
                if (Ingredient.Type.RAW_BURGER == HoldingDish.getCurrentIngridients().get(0).getType() ){
                    myLev.isFryingOnOvenInitialize = true;
                    myLev.fryingOnOven = true;
                    myLev.fryingOnOvenIngridient = HoldingDish.getCurrentIngridients().get(0);
                    HoldingDish.getCurrentIngridients().remove(0);
                    myLev.trackWithChef = HoldingDish;

                    System.out.println("Cooking burger");
                }


                break;
            case "plateStation":
                if (HoldingDish.getCurrentIngridients().size() == 0) {
                    for (int i = 0; i < myLev.dishingUpStack.getCurrentIngridients().size() ; i ++){
                        HoldingDish.addIngridientClass(myLev.dishingUpStack.getCurrentIngridients().get(i));
                    }
                    myLev.dishingUpStack = new Dish("asd");
                    Ingredient newPlate = new Ingredient(Ingredient.Type.PLATE);
                    newPlate.x = getX();
                    newPlate.y = getY();
                    // newPlate.setCenterY(getY());
                    myLev.extraIngri = newPlate;
                    HoldingDish.addIngridientClass(newPlate);
                    myLev.trackWithChef = HoldingDish;
                }
                else {
                    System.out.println("running this bit ");
                    for (int i = HoldingDish.getCurrentIngridients().size() - 1; i >= 0; i --){
                        myLev.dishingUpStack.addIngridientClass( HoldingDish.getCurrentIngridients().get(i));
                        HoldingDish.getCurrentIngridients().remove(i);

                    }
                    myLev.trackWithChef = HoldingDish;
                }

                break;
            case "deliveryStation":
                boolean isBurger = false;
                boolean hasCookedBurger = false;
                boolean hasChoppedTomatoes = false;
                boolean hasChoppedBun = false;
                for (int i = 0; i < HoldingDish.getCurrentIngridients().size(); i ++){
                    if (HoldingDish.getCurrentIngridients().get(i).getType() == Ingredient.Type.COOKED_BURGER){hasCookedBurger=true;}
                    if (HoldingDish.getCurrentIngridients().get(i).getType() == Ingredient.Type.CHOPPED_BUN){hasChoppedBun=true;}
                    if (HoldingDish.getCurrentIngridients().get(i).getType() == Ingredient.Type.CHOPPED_TOMATO){hasChoppedTomatoes = true;}
                }
                if (hasChoppedBun && hasChoppedTomatoes && hasCookedBurger){
                    System.out.println("This is a burger!!!!!!");
                    HoldingDish = new Dish("purge");
                    myLev.trackWithChef = HoldingDish;
                    if (myLev.orderArray.size() > 0){
                        myLev.orderArray.remove(0);
                    }
                }
                break;
            case "burgerStation":
                Ingredient newBurger = new Ingredient(Ingredient.Type.RAW_BURGER);
                newBurger.x = getX();
                newBurger.y = getY();
                // newPlate.setCenterY(getY());
                myLev.extraIngri = newBurger;
                HoldingDish.addIngridientClass(newBurger);
                myLev.trackWithChef = HoldingDish;

                break;
            case "tomatoStation":
                System.out.println("tomatoes");
                Ingredient newTomato = new Ingredient(Ingredient.Type.RAW_TOMATO);
                newTomato.x  =getX();
                newTomato.y = getY();
                HoldingDish.addIngridientClass(newTomato);
                myLev.trackWithChef = HoldingDish;

                break;
            case "bunStation":

                Ingredient newBuns = new Ingredient(Ingredient.Type.BUN);
                newBuns.x = getX();
                newBuns.y = getY();
                HoldingDish.addIngridientClass(newBuns);
                myLev.trackWithChef = HoldingDish;
                System.out.println("buns");
                default:
                return;


        }
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
