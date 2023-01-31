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

    private Dish HoldingDish = new Dish("new Dish", this);

    Level level;
    Level myLev;
    public boolean active = false;
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

    public Chef(Level level, String chefNo){
        myLev = level;
        String path = "chef/" + chefNo + "1_new.png";
        walkingSpeed = 120;
        currentState = State.STANDING;
        collisionLayer = level.getMapTileLayer(1);
        collideX=false;
        collideY=false;
        Texture chefSheet = new Texture(path);

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
            if (isCellBlocked(getX(), getY()))
                return true;
        return false;
    }
    private boolean collidesRight() {
        for(float step = 0; step < getHeight(); step += collisionLayer.getTileHeight() / 2)
            if (isCellBlocked(getX() + getWidth(), getY()))
                return true;
        return false;
    }

    private boolean collidesTop() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
            if (isCellBlocked(getX() - step, getY() + getHeight()/2)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesBottom() {
        for(float step = 0; step < getWidth(); step += collisionLayer.getTileWidth() / 2) {
            if (isCellBlocked(getX() + step, getY() + getHeight()/2)) {
                return true;
            }
        }
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
            case Input.Keys.Q:
                active = false;
                break;
            case Input.Keys.E:

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
                if (minDist < 38) {
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

                // this stops game from crashing if chef has no items in hand
                if (HoldingDish.getCurrentIngredients().size() == 0) { break; }
                if (HoldingDish.getCurrentIngredients().get(0).getType() == Ingredient.Type.UNTEN_BURGER){
                    HoldingDish.getCurrentIngredients().get(0).setType(Ingredient.Type.RAW_BURGER);
                }

                break;
            case "cutStation":

                /*
                if (HoldingDish.getCurrentIngredients().size() == 0){
                    if (myLev.onChoppingBoardIngredient.getType() == Ingredient.Type.ChoppedTomato) {
                        System.out.println("grabbing chopped tomato");
                        HoldingDish.addIngredientClass(myLev.fryingOnOvenIngredient);
                        myLev.fryingOnOvenIngredient = null;
                        myLev.trackWithChef = HoldingDish;
                    }
                    if (myLev.onChoppingBoardIngredient.getType() == Ingredient.Type.ChoppedLettuce) {
                        System.out.println("grabbing chopped lettuce");
                        HoldingDish.addIngredientClass(myLev.fryingOnOvenIngredient);
                        myLev.fryingOnOvenIngredient = null;
                        myLev.trackWithChef = HoldingDish;
                    }
                }

                */

                // this stops game from crashing if the chef has no items in their hand
                if (HoldingDish.getCurrentIngredients().size() == 0) { break; }

                if (HoldingDish.getCurrentIngredients().get(0).getType() == Ingredient.Type.RAW_TOMATO){
                    //TODO implement delay on the cutting
                    System.out.println("cutting tomato");
                    HoldingDish.getCurrentIngredients().get(0).setType(Ingredient.Type.CHOPPED_TOMATO);
                }
                if (HoldingDish.getCurrentIngredients().get(0).getType() == Ingredient.Type.BUN){
                    System.out.println("cutting bun");
                    HoldingDish.getCurrentIngredients().get(0).setType(Ingredient.Type.CHOPPED_BUN);
                }
                if (HoldingDish.getCurrentIngredients().get(0).getType() == Ingredient.Type.RAW_LETTUCE){
                    System.out.println("cutting lettuce");
                    HoldingDish.getCurrentIngredients().get(0).setType(Ingredient.Type.CHOPPED_LETTUCE);
                }

                break;
            case "cookStation":

                if (HoldingDish.getCurrentIngredients().size() == 0){
                    if (myLev.cookedBurgerOnOven == false) { break; }

                    else if (myLev.fryingOnOvenIngredient.getType() == Ingredient.Type.COOKED_BURGER){
                        System.out.println("grabbing cooked burger");
                        HoldingDish.addIngredientClass(myLev.fryingOnOvenIngredient);
                        myLev.fryingOnOvenIngredient = null;
                        myLev.cookedBurgerOnOven = false;
                        myLev.trackWithChef = HoldingDish;
                    }
                }

                // this stops game from crashing if the chef has no items in their hand
                if (HoldingDish.getCurrentIngredients().size() == 0) { break; }

                if (Ingredient.Type.RAW_BURGER == HoldingDish.getCurrentIngredients().get(0).getType() ){
                    myLev.isFryingOnOvenInitialize = true;
                    myLev.fryingOnOven = true;
                    myLev.fryingOnOvenIngredient = HoldingDish.getCurrentIngredients().get(0);
                    HoldingDish.getCurrentIngredients().remove(0);
                    myLev.trackWithChef = HoldingDish;

                    System.out.println("Cooking burger");
                }


                break;
            case "plateStation":
                if (HoldingDish.getCurrentIngredients().size() == 0) {
                    for (int i = 0; i < myLev.dishingUpStack.getCurrentIngredients().size() ; i ++){
                        HoldingDish.addIngredientClass(myLev.dishingUpStack.getCurrentIngredients().get(i));
                    }
                    myLev.dishingUpStack = new Dish("asd", this);
                    Ingredient newPlate = new Ingredient(Ingredient.Type.PLATE, this);
                    newPlate.x = getX();
                    newPlate.y = getY();
                    // newPlate.setCenterY(getY());
                    myLev.extraIngri = newPlate;
                    HoldingDish.addIngredientClass(newPlate);
                    myLev.trackWithChef = HoldingDish;
                }
                else {
                    System.out.println("running this bit ");
                    for (int i = HoldingDish.getCurrentIngredients().size() - 1; i >= 0; i --){
                        myLev.dishingUpStack.addIngredientClass( HoldingDish.getCurrentIngredients().get(i));
                        HoldingDish.getCurrentIngredients().remove(i);

                    }
                    myLev.trackWithChef = HoldingDish;
                }

                break;
            case "deliveryStation":
                boolean isBurger = false;
                boolean hasCookedBurger = false;
                boolean hasChoppedTomatoes = false;
                boolean hasChoppedBun = false;
                boolean hasChoppedLettuce = false;
                for (int i = 0; i < HoldingDish.getCurrentIngredients().size(); i ++){
                    if (HoldingDish.getCurrentIngredients().get(i).getType() == Ingredient.Type.COOKED_BURGER){hasCookedBurger=true;}
                    if (HoldingDish.getCurrentIngredients().get(i).getType() == Ingredient.Type.CHOPPED_BUN){hasChoppedBun=true;}
                    if (HoldingDish.getCurrentIngredients().get(i).getType() == Ingredient.Type.CHOPPED_TOMATO){hasChoppedTomatoes = true;}
                    if (HoldingDish.getCurrentIngredients().get(i).getType() == Ingredient.Type.CHOPPED_LETTUCE){hasChoppedLettuce = true;}
                }
                if (hasChoppedBun && hasChoppedTomatoes && hasCookedBurger && hasChoppedLettuce ){
                    System.out.println("This is a burger!!!!!!");
                    HoldingDish = new Dish("purge", this);
                    myLev.trackWithChef = HoldingDish;

                        for (int i = 0; i < myLev.orderArray.size();i ++){
                            if (myLev.orderArray.get(i) == "Burger"){
                                myLev.orderArray.remove(i);
                                break;
                            }
                        }

                }

                else {
                    boolean isSalad = false;
                    hasChoppedTomatoes = false;
                    hasChoppedLettuce = false;
                    for (int i = 0; i < HoldingDish.getCurrentIngredients().size(); i ++) {
                        if (HoldingDish.getCurrentIngredients().get(i).getType() == Ingredient.Type.CHOPPED_TOMATO) {
                            hasChoppedTomatoes = true;
                        }
                        if (HoldingDish.getCurrentIngredients().get(i).getType() == Ingredient.Type.CHOPPED_LETTUCE) {
                            hasChoppedLettuce = true;
                        }
                    }
                    if (hasChoppedTomatoes && hasChoppedLettuce) {
                        System.out.println("This is a salad!!!!!!");
                        HoldingDish = new Dish("purge", this);
                        myLev.trackWithChef = HoldingDish;
                        for (int i = 0; i < myLev.orderArray.size();i ++){
                            if (myLev.orderArray.get(i) == "Salad"){
                                myLev.orderArray.remove(i);
                                break;
                            }
                        }
                    }
                }

                break;
            case "burgerStation":
                System.out.println("burger patty");
                Ingredient newBurger = new Ingredient(Ingredient.Type.UNTEN_BURGER, this);
                newBurger.x = getX();
                newBurger.y = getY();
                // newPlate.setCenterY(getY());
                myLev.extraIngri = newBurger;
                HoldingDish.addIngredientClass(newBurger);
                myLev.trackWithChef = HoldingDish;

                break;
            case "tomatoStation":
                System.out.println("raw tomato");
                Ingredient newTomato = new Ingredient(Ingredient.Type.RAW_TOMATO, this);
                newTomato.x  =getX();
                newTomato.y = getY();
                HoldingDish.addIngredientClass(newTomato);
                myLev.trackWithChef = HoldingDish;

                break;
            case "bunStation":
                System.out.println("buns");
                Ingredient newBuns = new Ingredient(Ingredient.Type.BUN, this);
                newBuns.x = getX();
                newBuns.y = getY();
                HoldingDish.addIngredientClass(newBuns);
                myLev.trackWithChef = HoldingDish;

                break;

            case "lettuceStation":
                System.out.println("lettuce");
                Ingredient newLettuce = new Ingredient(Ingredient.Type.RAW_LETTUCE, this);
                newLettuce.x = getX();
                newLettuce.y = getY();
                HoldingDish.addIngredientClass(newLettuce);
                myLev.trackWithChef = HoldingDish;

                break;

            case "binStation":
                if (HoldingDish.getCurrentIngredients().size() > 0 ){
                    for (int i = 0 ; i < HoldingDish.getCurrentIngredients().size(); i ++){
                        HoldingDish.getCurrentIngredients().remove(i);
                    }
                    myLev.trackWithChef = HoldingDish;
                }

                System.out.println("you are at the bin station");

            default: return;


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
