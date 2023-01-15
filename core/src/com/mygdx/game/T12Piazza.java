package com.mygdx.game;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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

	Level myLevelManager;


	@Override
	public void create () {
		batch = new SpriteBatch();
		chef_1 = new Texture("chef/a2.png");
		chef_2 = new Texture("chef/b2.png");
		chef_1_default = new TextureRegion(chef_1, 34, 12, 30, 40);
		myLevelManager = new Level();
		myLevelManager.initialiseLevel();


	}

	@Override
	public void render () {

		if (myLevelManager.getTimeToNextCustomer() < myLevelManager.getTimeElapsedMilliSeconds()) {
			//This checks whether enough time has passed in order to spawn a new customer
			//System.out.println("spawn Next Customer");
			myLevelManager.nextCustomer();
		}
		if (myLevelManager.getTimeSinceLastIO() > myLevelManager.getMinIdleTime()){
			//This is statement is checking the time since the last user I/O. If the time has been suffiently long it request the game move to the idle mode
			//System.out.println("Activate idle");
		}
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
