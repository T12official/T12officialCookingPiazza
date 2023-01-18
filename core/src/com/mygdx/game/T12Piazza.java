package com.mygdx.game;
import java.util.*;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.ScreenUtils;


public class T12Piazza extends Game {
	SpriteBatch batch;
	Chef chef_1;
	Texture chef_2;
	TextureRegion chef_1_default;

	Level myLevelManager;
	float stateTime;




	@Override
	public void create () {
		setScreen(new Level());
		batch = new SpriteBatch();
		chef_2 = new Texture("chef/b2.png");
		//chef_1_default = new TextureRegion(chef_1, 34, 12, 30, 40);

		myLevelManager = new Level();
		chef_1 = new Chef(myLevelManager);
		myLevelManager.initialiseLevel();

	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void render () {
		super.render();
		if (myLevelManager.getTimeToNextCustomer() < myLevelManager.getTimeElapsedMilliSeconds()) {
			//This checks whether enough time has passed in order to spawn a new customer
			//System.out.println("spawn Next Customer");
			myLevelManager.nextCustomer();
		}
		if (myLevelManager.getTimeSinceLastIO() > myLevelManager.getMinIdleTime()){
			//This is statement is checking the time since the last user I/O. If the time has been suffiently long it request the game move to the idle mode
			//System.out.println("Activate idle");
		}


		stateTime += Gdx.graphics.getDeltaTime(); // Accumulate elapsed animation time
		TextureRegion chefFrame = chef_1.playerWalk.getKeyFrame(stateTime, true);

		batch.begin();

		if (chef_1.isStanding()){
			chefFrame = chef_1.chefStanding;
		}
		chef_1.handleInput();
		batch.draw(chefFrame, chef_1.getX(), chef_1.getY(), 32, 32);
		batch.end();

	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	@Override
	public void dispose () {
		super.dispose();
		batch.dispose();
		chef_1.dispose();
	}
}
