package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class T12Piazza extends Game {

	Chef chef_1;
	Texture chef_2;
	TextureRegion chef_1_default;

	Level myLevelManager;





	@Override
	public void create () {
		setScreen(new Level());
		chef_2 = new Texture("chef/b2.png");
		//chef_1_default = new TextureRegion(chef_1, 34, 12, 30, 40);

		myLevelManager = new Level();

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
		chef_1.dispose();
	}
}
