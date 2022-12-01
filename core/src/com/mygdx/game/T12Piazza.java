package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class T12Piazza extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chef_1;
	Texture chef_2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		chef_1 = new Texture("chef/a2.png");
		chef_2 = new Texture("chef/b2.png");
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		chef_1.dispose();
	}
}
