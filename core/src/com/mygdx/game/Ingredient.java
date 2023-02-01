package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ingredient extends Sprite {
    private Type type;
    public Chef myChef;
    public Float  x;
    public Float y;

    public enum Type { RAW_BURGER, COOKED_BURGER, RAW_TOMATO, CHOPPED_TOMATO, BUN, CHOPPED_BUN, PLATE, RAW_LETTUCE, CHOPPED_LETTUCE, UNTEN_BURGER }
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
    Texture untenderBurger;

    public Ingredient(Type myType, Chef chef){
        type = myType;
        myChef = chef;

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
        untenderBurger = new Texture(Gdx.files.internal("ingredients/untenderized_raw_burger.png"));
    }

    @Override
    public void draw(Batch batch) {
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
            case RAW_LETTUCE:
                currentTexture = rawLettuceTexture;
                break;
            case CHOPPED_LETTUCE:
                currentTexture = choppedLettuceTexture;
                break;
            case UNTEN_BURGER:
                currentTexture = untenderBurger;
        }
        if (x == null || y == null) {
            batch.draw(currentTexture, 10, 10);
        }
        else {
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
