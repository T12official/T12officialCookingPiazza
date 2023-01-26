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
    private Sprite devieryStation;
    private Sprite placeHolder;

    public Station(Level level){

        Texture texture = new Texture(Gdx.files.internal("Tiles/tabletop_wallknife.png"));//Load image from pathway to use as for the sprite
        tenderStation = new Sprite(texture, 0, 0, 12,24);//srcWidth and srcHeight need to correspond with the values of the image size
        Texture texture2 = new Texture(Gdx.files.internal("Tiles/tabletop.png"));
        placeHolder= new Sprite(texture2, 0 ,0, 12, 24);
        Texture texture3 = new Texture(Gdx.files.internal("Tiles/counterFillin.png"));
        devieryStation = new Sprite(texture3, 30,30,12,24);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.E ) {
            interacted = true;
        }
        return true;
    }

    @Override
    public void draw(Batch batch) {
        batch.draw(tenderStation, 30, 110, 40, 45);//Place the sprite and set it size in-game
        batch.draw(devieryStation, 185,0,40,45);
        if (interacted == true) {
            batch.draw(placeHolder, 10, 10, 40, 45);
        }
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
