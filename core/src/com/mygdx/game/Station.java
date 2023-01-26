package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Station extends Sprite implements InputProcessor {
    private boolean interacted = false;

    private SpriteBatch batch;

    private Sprite tenderStation;//Create new sprite
    private Sprite cutStation;

    private Sprite cookStation;

    private Sprite plateStation;
    private Sprite deliveryStation;


    public Sprite[] getSprites(){
        Sprite[] a = {tenderStation, cutStation, cookStation, plateStation, deliveryStation};
        return a;

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
        batch.draw(tenderStation, 30, 110, 30, 45);//Place the sprite and set it size in-game
        batch.draw(cutStation, 60,110,30,45);
        batch.draw(cookStation, 90, 110, 30, 45);
        batch.draw(plateStation, 120, 110, 30, 45);
        batch.draw(deliveryStation, 150, 110, 30, 45);

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
