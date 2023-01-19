package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.*;
public class Level implements Screen {
    ArrayList<Customer> customerList = new ArrayList<>();
    ArrayList<Chef> chefList = new ArrayList<>();
    long timer;
    long idleTime;
    List<List<String>> gridArray;
    String gameMode;
    //TimeUtils.millis();
    long timeToNextCustomer;
    long getTimeToIdleGame;

    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private OrthographicCamera camera;
    float stateTime;
    SpriteBatch batch;
    private Chef chef1;


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tiledMapRenderer.setView(camera);
        batch = new SpriteBatch();
        chefList = new ArrayList<>();

        int[] layers = {0,1, 2};
        tiledMapRenderer.render(layers);

        batch.begin();
        chef1.draw(batch, delta);
        batch.end();



    }

    @Override
    public void show() {
        tiledMap = new TmxMapLoader().load("gameMaps/level1.tmx");
        chef1 = new Chef(this);
        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
        camera = new OrthographicCamera();
    }

    @Override
    public void hide() {
        dispose();
    }

    public long getTimeToNextCustomer(){
        return timeToNextCustomer;
    }


    public void readAssetFile(String gameFile){

        //This function reads read in a gameMap file and populates the gridArray variable which
        //The gridArray is a 2d array that contains a map of the game world

        FileHandle handle =  Gdx.files.local(gameFile);
        String text = handle.readString();
        //System.out.println(text);
        String[] myStrings = text.split("\n");
        for (int i = 0 ; i < myStrings.length ; i ++){
            for (int x = 0 ; x < myStrings.length ; x ++){
                if (x == 0){gridArray.add(new ArrayList<String>());}
                gridArray.get(i).add(String.valueOf(myStrings[i].charAt(x)));
            }


        }
        System.out.println( gridArray.toString());

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
            customerList.get(customerList.size() - 1);
            customerList.remove(customerList.size() - 1);
            timer = TimeUtils.millis();
        }
    }

    @Override
    public void dispose() {
        tiledMap.dispose();
        batch.dispose();
    }
}
