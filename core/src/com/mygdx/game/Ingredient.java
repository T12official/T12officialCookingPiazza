package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ingredient extends Sprite {
    private Type type;
    public Float  x;
    public Float y;
    // TODO: add plate & dish types

    public enum Type { RAW_BURGER, COOKED_BURGER, RAW_TOMATO, CHOPPED_TOMATO, BUN, CHOPPED_BUN, PLATE, RAW_LETTUCE, CHOPPED_LETTUCE }
    Texture rawBurgerTexture;
    Texture cookedBurgerTexture;
    Texture rawTomatoTexture;
    Texture choppedTomatoTexture;
    Texture bunTexture;
    Texture choppedBunTexture;
    Texture currentTexture;
    Texture plateTexture;
    Texture rawLettuceTexture;
    Texture choppedLettuceTexture;

    Texture rawBurgerTexture0;

    public Ingredient(Type myType){
        type = myType;

        rawBurgerTexture = new Texture(Gdx.files.internal("ingredients/raw_burger.png"));

        cookedBurgerTexture = new Texture(Gdx.files.internal("ingredients/cooked_burger.png"));
        bunTexture = new Texture(Gdx.files.internal("ingredients/uncut_bun.png"));
        choppedBunTexture = new Texture(Gdx.files.internal("ingredients/cut_bun_bottom.png"));
        // currently only implementing the bottom half of a cut bun
        rawLettuceTexture = new Texture(Gdx.files.internal("ingredients/whole_lettuce.png"));
        choppedLettuceTexture = new Texture(Gdx.files.internal("ingredients/cut_lettuce.png"));
        rawTomatoTexture = new Texture(Gdx.files.internal("ingredients/tomato_whole.png"));
        choppedTomatoTexture = new Texture(Gdx.files.internal("ingredients/tomato_chopped.png"));
        plateTexture = new Texture(Gdx.files.internal("ingredients/plate_empty.png"));

        //type = Type.RAW_TOMATO;
    }

    @Override
    public void draw(Batch batch) {
        //super.draw(batch);
        switch (type){
            case RAW_TOMATO:
                currentTexture = rawTomatoTexture;
                break;
            case CHOPPED_TOMATO:
                currentTexture = choppedTomatoTexture;
                break;
            case BUN:
                currentTexture = bunTexture;
                break;
            case RAW_BURGER:
                currentTexture = rawBurgerTexture;
                break;
            case COOKED_BURGER:
                currentTexture = cookedBurgerTexture;
                break;
            case CHOPPED_BUN:
                currentTexture = choppedBunTexture;
                break;
            case PLATE:
                currentTexture = plateTexture;
                break;
                // TODO: add PLATE case
            case RAW_LETTUCE:
                currentTexture = rawLettuceTexture;
                break;
            case CHOPPED_LETTUCE:
                currentTexture = choppedLettuceTexture;
        }
        if (x == null || y == null) {
            batch.draw(currentTexture, 10, 10);
        }else {
            batch.draw(currentTexture,  x,  y);
        }
    }

    public Type getType() {
        return type;
    }
    public void setType(Type a){
        type = a;
    }
}
