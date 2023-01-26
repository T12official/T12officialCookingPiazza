package com.mygdx.game;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ingredient extends Sprite {
    private final Type type;
    // TODO: add plate

    public enum Type { RAW_BURGER, COOKED_BURGER, RAW_TOMATO, CHOPPED_TOMATO, BUN, CHOPPED_BUN }
    Texture rawBurgerTexture;
    Texture cookedBurgerTexture;
    Texture rawTomatoTexture;
    Texture choppedTomatoTexture;
    Texture bunTexture;
    Texture choppedBunTexture;
    Texture currentTexture;

    public Ingredient(){
        // TODO: create function and optimize
        Pixmap pixmap = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap.setColor( 1, 0, 0, 0.75f );
        pixmap.fillCircle( 8, 8, 16 );
        rawBurgerTexture = new Texture( pixmap );

        Pixmap pixmap1 = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap1.setColor( 1, 1, 1, 0.7f );
        pixmap1.fillCircle( 8, 8, 16 );
        cookedBurgerTexture = new Texture(pixmap1);

        Pixmap pixmap2 = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap2.setColor( 1, 0, 0, 0.75f );
        pixmap2.fillCircle( 8, 8, 16 );
        rawTomatoTexture = new Texture(pixmap2);

        Pixmap pixmap3= new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap3.setColor( 1, 0, 0, 0.75f );
        pixmap3.fillCircle( 8, 8, 4 );
        choppedTomatoTexture = new Texture(pixmap3);

        Pixmap pixmap4 = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap4.setColor(1, 1, 100, 0.75f);
        pixmap4.fillCircle( 8, 8, 4 );
        bunTexture = new Texture(pixmap4);

        Pixmap pixmap5 = new Pixmap( 16, 16, Pixmap.Format.RGBA8888 );
        pixmap5.setColor(1, 1, 100, 0.75f);
        pixmap5.fillCircle( 8, 8, 4 );
        choppedBunTexture = new Texture(pixmap5);

        type = Type.RAW_TOMATO;
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
            // TODO: add PLATE case
        }
        batch.draw(currentTexture, 10, 10);
    }
}
