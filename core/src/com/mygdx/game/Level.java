package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Level implements Screen {
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Chef> chefList = new ArrayList<>();
    long timer;
    long idleTime;
    List<List<String>> gridArray;
    String gameMode;
    long timeToNextCustomer;
    long getTimeToIdleGame;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    SpriteBatch batch;
    public int currentChef = 0;
    private Station station;

    final static int MAP_HEIGHT = 10;
    final static int MAP_WIDTH = 7;
    private Stage stage;
    private final InputMultiplexer inputMultiplexer = new InputMultiplexer();
    boolean initialize = false;
    boolean primary = true;
    Ingredient extraIngredient;
    public Dish trackWithChef;

    List<String> orderArray = new ArrayList<>();
    Overlay myOverlay;

    boolean fryingOnOven = false;
    boolean cookedBurgerOnOven = false;
    boolean isFryingOnOvenInitialize = false;
    Ingredient fryingOnOvenIngredient;

    long burgerCookingTime;
    Dish dishingUpStack;

    public Level() {
    }

    @Override
    public void render(float delta) {
        // Check if we need to switch chefs
        if (!getChef().active) {
            switchChef();
        }
        Table table = new Table();
        table.setFillParent(false);
        table.setPosition(100,50);
        table.sizeBy(100,100);
        // table.setDebug(true);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        batch = new SpriteBatch();
        Ingredient ingredient = new Ingredient(Ingredient.Type.RAW_TOMATO, getChef() );

        tiledMapRenderer.render();
        camera.position.set(getChef().getX() + getChef().getWidth() / 2, getChef().getY() + getChef().getHeight() / 2, 0);
        Stage stage = new Stage(new FitViewport(32 * MAP_WIDTH, 32 * MAP_HEIGHT, camera));
        myOverlay = new Overlay();
        myOverlay.setUpTable(stage);
        myOverlay.setTableBackgroundColor(230,0,0,60);
        myOverlay.addText("Active Orders:");

        if (initialize){
            Random myRand = new Random();
            timeToNextCustomer = myRand.nextInt(10000) + 10000;
            getTimeToIdleGame = 15000;
            timer = TimeUtils.millis();
            initialize = false;

            // Generate customers
            if (primary){
                for (int i = 0; i < 5; i++) {
                    if (i % 2 == 0) {
                        customerList.add(new Customer(new Dish("Burger", getChef())));
                    }
                    else {
                        customerList.add(new Customer(new Dish("Salad", getChef())));
                    }
                }
                primary = false;
            }
        }
        else {
            if (getTimeElapsedMilliSeconds() > timeToNextCustomer){
                initialize = true;
                if (customerList.size() > 0){
                    // System.out.println("I ran");
                    orderArray.add(customerList.get(customerList.size() - 1).order.myDishName);
                    customerList.remove(customerList.size() - 1);
                }
            }
        }
        updateOverlay();
        stage.draw();
        batch.begin();
        batch.setProjectionMatrix(camera.combined);
        for (Chef chef : chefList) {
            chef.draw(batch, delta);
        }
        ingredient.draw(batch);
        station.draw(batch);
        // Spawn ingredients at their respective chefs
        List<Ingredient> setPositions = trackWithChef.getCurrentIngredients();
        for (Ingredient temp : setPositions) {
            temp.x = temp.myChef.getX();
            temp.y = temp.myChef.getY();
            temp.draw(batch);
        }
        if (fryingOnOven) {
            if (isFryingOnOvenInitialize){
                burgerCookingTime = TimeUtils.millis();
                isFryingOnOvenInitialize = false;
            }
            else {
                if (TimeUtils.timeSinceMillis(burgerCookingTime) > 8000){
                    // System.out.println("Burger cooked");
                    fryingOnOven = false;
                    cookedBurgerOnOven = true;
                    fryingOnOvenIngredient.setType(Ingredient.Type.COOKED_BURGER);
                }
            }
        }
        if (fryingOnOvenIngredient != null) {
            fryingOnOvenIngredient.x = 90f;
            fryingOnOvenIngredient.y = 110f;
            fryingOnOvenIngredient.draw(batch);
        }
        for (int i = 0 ; i < dishingUpStack.getCurrentIngredients().size(); i ++){
            dishingUpStack.getCurrentIngredients().get(i).x = 123f;
            dishingUpStack.getCurrentIngredients().get(i).y = 123f;
            dishingUpStack.getCurrentIngredients().get(i).draw(batch);
        }
        batch.end();
        camera.update();
    }

    private void updateOverlay(){
        for (String s : orderArray) {
            myOverlay.addText(s);
        }
    }

    public Map<String, List<Double>> getSpriteData(){
        return station.getSpriteData();
    }

    @Override
    public void show() {
        // load the level
        tiledMap = new TmxMapLoader().load("gameMaps/level2.tmx");

        // Create chefs. 2nd argument chefNo refers to the texture file used from /assets/chef.
        // This game can support as many chefs as you insert into ChefList, pressing Q will cycle through them.
        chefList.add(new Chef(this, "a"));
        chefList.add(new Chef(this, "b"));
        getChef().active = true;
        station = new Station(this);
        dishingUpStack  = new Dish("new dish", getChef());
        trackWithChef = new Dish("new dish", getChef());

        // Listen to inputs from the current chef and the station interactions
        InputProcessor[] cars = {getChef(), station};
        inputMultiplexer.setProcessors(cars);
        inputMultiplexer.getProcessors();
        Gdx.input.setInputProcessor(inputMultiplexer);

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        float aspectRatio = (float) (Gdx.graphics.getHeight() / Gdx.graphics.getWidth());
        float GAME_HEIGHT = 100;
        float GAME_WIDTH = 200;
        camera = new OrthographicCamera(GAME_HEIGHT * aspectRatio, GAME_WIDTH * aspectRatio);
    }

    public void switchChef(){
        // Increment chefs and mark it as active, then set the input processor to the new chef.
        // You're meant to use addProcessor and removeProcessor... but I couldn't get it to work ¯\_(ツ)_/¯
        currentChef += 1;
        InputProcessor[] cars = {getChef(), station};
        inputMultiplexer.setProcessors(cars);
        getChef().active = true;
    }

    public Chef getChef(){
        // Classic mod operation logic to cycle through chefs
        return chefList.get(currentChef%chefList.size());
    }

    @Override
    public void hide() {
        dispose();
    }

    public long getTimeToNextCustomer(){
        return timeToNextCustomer;
    }

    public void readAssetFile(String gameFile){
        // This function reads in a gameMap file and populates the gridArray variable
        // The gridArray is a 2d array that contains a map of the game world
        FileHandle handle =  Gdx.files.local(gameFile);
        String text = handle.readString();
        // System.out.println(text);
        String[] myStrings = text.split("\n");
        for (int i = 0 ; i < myStrings.length ; i ++){
            for (int x = 0 ; x < myStrings.length ; x ++){
                if (x == 0){gridArray.add(new ArrayList<String>());}
                gridArray.get(i).add(String.valueOf(myStrings[i].charAt(x)));
            }
        }
        // System.out.println( gridArray.toString());
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width;
        camera.viewportHeight = height;
        camera.update();
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    public void initialiseLevel(){
        timeToNextCustomer = 10000;
        getTimeToIdleGame = 15000;

        timer = TimeUtils.millis();
        idleTime = TimeUtils.millis();
        gridArray = new ArrayList<>();
        readAssetFile("gameMaps/gameMap.txt");
    }

    public Sprite[] getSprites (){
        return station.getSprites();
    }

    public long getTimeElapsedMilliSeconds(){
        return TimeUtils.timeSinceMillis(timer);
    }

    public long getTimeSinceLastIO(){
        return TimeUtils.timeSinceMillis(idleTime);
    }

    public long getMinIdleTime(){
        return getTimeToIdleGame;
    }

    public void nextCustomer(){
        if (customerList.size() > 0){
            customerList.remove(customerList.size() - 1);
            timer = TimeUtils.millis();
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        batch.dispose();
    }

    public TiledMapTileLayer getMapTileLayer(int i) {
        return (TiledMapTileLayer) tiledMap.getLayers().get(i);
    }
}
