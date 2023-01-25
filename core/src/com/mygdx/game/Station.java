package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Station extends Sprite implements InputProcessor {
    private boolean interacted = false;

    private SpriteBatch batch;

    private Sprite tenderStation;

    public void create(){
        batch = new SpriteBatch();
    }
    
    public Station(){

        Texture texture = new Texture(Gdx.files.internal("tabletop_wallknife.png"));
        tenderStation = new Sprite(texture, 20, 20, 50, 50);
        tenderStation.setPosition(20,20);
    }

    public void render(){
        batch.begin();
        tenderStation.draw(batch);
        batch.end();
    }

    



    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.E ) {
            interacted = true;
        }
        return true;
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

    //enum Type{FRIDGE, SERVICE, COOKER}
    public Ingredient[] ingredients;



}
