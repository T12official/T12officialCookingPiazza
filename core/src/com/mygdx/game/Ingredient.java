package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ingredient extends Sprite {
    private Type type;
    public Float  x;
    public Float y;

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

    public Ingredient(Type myType){
        type = myType;
        rawBurgerTexture = generate_texture(16, 1, 0, 0, 0.75f);
        cookedBurgerTexture = generate_texture(16, 1, 1,1, 0.7f);
        rawTomatoTexture = generate_texture(16, 1, 0, 0, 0.75f);
        choppedTomatoTexture = generate_texture(4, 1, 0, 0, 0.75f);
        bunTexture = generate_texture(4, 1, 1, 100, 0.75f);
        choppedBunTexture = generate_texture(4, 55, 43, 100, 0.75f);
        plateTexture = generate_texture(4, 60, 60, 60, 0.75f);
        rawLettuceTexture = generate_texture(16, 0, 1,0, 0.75f);
        choppedLettuceTexture = generate_texture(4, 0, 1, 0, 0.75f);
    }

    private Texture generate_texture(int radius, int r, int g, int b, float a){
        Pixmap pixmap = new Pixmap(16, 16, Pixmap.Format.RGBA8888);
        pixmap.setColor(r, g, b, a);
        pixmap.fillCircle(8, 8, radius);
        return new Texture(pixmap);
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
            case RAW_LETTUCE:
                currentTexture = rawTomatoTexture;
                break;
            case CHOPPED_LETTUCE:
                currentTexture = choppedTomatoTexture;
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
