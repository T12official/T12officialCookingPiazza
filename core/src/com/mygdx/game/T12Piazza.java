package com.mygdx.game;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class T12Piazza extends ApplicationAdapter {
	SpriteBatch batch;
	Texture chef_1;
	Texture chef_2;
	TextureRegion chef_1_default;

	@Override
	public void create () {
		batch = new SpriteBatch();
		chef_1 = new Texture("chef/a2.png");
		chef_2 = new Texture("chef/b2.png");
		chef_1_default = new TextureRegion(chef_1, 34, 12, 30, 40);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
		batch.draw(chef_1_default, 400, 200);
		batch.end();
	}

	@Override
	public void dispose () {
		batch.dispose();
		chef_1.dispose();
	}
}
