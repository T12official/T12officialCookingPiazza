package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Station extends Sprite implements InputProcessor {
    private SpriteBatch batch;
    private Sprite tenderStation;  // Create new sprite

    public Station(){
        Texture texture = new Texture(Gdx.files.internal("Tiles/tabletop_wallknife.png"));  // Load image from pathway to use as for the sprite
        tenderStation = new Sprite(texture, 0, 0, 12,24);  // srcWidth and srcHeight need to correspond with the values of the image size
    }

    @Override
    public void draw(Batch batch){
        batch.draw(tenderStation, 30, 110, 40, 45);  // Place the sprite and set it size in-game
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.E ) {
            boolean interacted = true;
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
