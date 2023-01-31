package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Station extends Sprite implements InputProcessor {
    private boolean interacted = false;

    private SpriteBatch batch;
    private Sprite tenderStation;  // Create new sprite
    private Sprite cutStation;
    private Sprite cookStation;
    private Sprite plateStation;
    private Sprite deliveryStation;
    private Sprite burgerStation;
    private Sprite tomatoStation;
    private Sprite bunStation;

    //possibly remove tenderStation
    //add lettuceStation - from the commented out code
    private Sprite lettuceStation;

    public Sprite[] getSprites(){
        Sprite[] a = {tenderStation, cutStation, cookStation, plateStation, deliveryStation};
        return a;

    }

    public Map<String, List<Double>> getSpriteData(){
        Map<String, List<Double>> dictionary = new HashMap<String, List<Double>>();
        List<Double> x_y = Arrays.asList(30.0,110.0);
        dictionary.put("tenderStation", x_y );
        List<Double> x_y2 = Arrays.asList(60.0,110.0);
        dictionary.put("cutStation", x_y2 );
        List<Double> x_y3 = Arrays.asList(90.0,110.0);
        dictionary.put("cookStation", x_y3 );
        List<Double> x_y4 = Arrays.asList(120.0,110.0);
        dictionary.put("plateStation", x_y4 );
        List<Double> x_y5 = Arrays.asList(150.0,110.0);
        dictionary.put("deliveryStation", x_y5 );
        List<Double> x_y6 = Arrays.asList(180.0,110.0);
        dictionary.put("burgerStation", x_y6 );
        List<Double> x_y7 = Arrays.asList(30.0,205.0);
        dictionary.put("tomatoStation", x_y7 );
        List<Double> x_y8 = Arrays.asList(60.0,205.0);
        dictionary.put("bunStation", x_y8 );

        //Added lettuce station
        List<Double> x_y9 = Arrays.asList(90.0,205.0);
        dictionary.put("lettuceStation", x_y9 );

        return dictionary;
    }

    public Station(Level level){

        Texture texture1 = new Texture(Gdx.files.internal("Tiles/tabletop_tenderising.png"));//Load image from pathway to use as for the sprite
        tenderStation = new Sprite(texture1, 0, 0, 12,24);//srcWidth and srcHeight need to correspond with the values of the image size

        Texture texture2 = new Texture(Gdx.files.internal("Tiles/tabletop_cutting_board.png"));
        cutStation= new Sprite(texture2, 0 ,0, 12, 24);

        Texture texture3 = new Texture(Gdx.files.internal("Tiles/kitchen_stove.png"));
        cookStation = new Sprite(texture3, 0,0, 12, 24);

        Texture texture4 = new Texture(Gdx.files.internal("Tiles/tabletop_plate.png"));
        plateStation = new Sprite(texture4, 0,0,12,24);

        Texture texture5 = new Texture(Gdx.files.internal("Tiles/tabletop_delivery_station.png"));
        deliveryStation = new Sprite(texture5, 0,0,12,24);

        Texture texture6 = new Texture(Gdx.files.internal("Tiles/tabletop.png"));
        burgerStation = new Sprite(texture6, 0,0,12,24);

        Texture texture7 = new Texture(Gdx.files.internal("Tiles/tabletop.png"));
        tomatoStation = new Sprite(texture7, 0,0,12,24);

        Texture texture8 = new Texture(Gdx.files.internal("Tiles/tabletop.png"));
        bunStation = new Sprite(texture8, 0,0,12,24);

        //will need to replace all tabletop.png's with an image of each ingredient each station is storing
        Texture texture9 = new Texture(Gdx.files.internal("Tiles/tabletop.png"));
        lettuceStation = new Sprite(texture9, 0,0,12,24);

        Sprite[] mySprites = {tenderStation, cutStation, cookStation, plateStation, deliveryStation};
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.E ) {
            System.out.println("sdusfdskhkfdukhweffweqkhuwefwefkhuwefkhuwefkhfweukhfwehukefw");
            interacted = true;
        }
        return true;
    }

    @Override
    public void draw(Batch batch) {
        // Place the sprite and set its size in-game
        batch.draw(tenderStation, 30, 110, 30, 45);
        batch.draw(cutStation, 60,110,30,45);
        batch.draw(cookStation, 90, 110, 30, 45);
        batch.draw(plateStation, 120, 110, 30, 45);
        batch.draw(deliveryStation, 150, 110, 30, 45);
        batch.draw(burgerStation, 180,110,30,45);
        batch.draw(tomatoStation, 30,205, 30,45);
        batch.draw(bunStation, 60,205,30,45);

        batch.draw(lettuceStation, 90, 205, 30, 45);
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
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

    public Ingredient[] ingredients;
}
