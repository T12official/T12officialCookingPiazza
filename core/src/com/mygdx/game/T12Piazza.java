package com.mygdx.game;

import com.badlogic.gdx.Game;

public class T12Piazza extends Game {
	Level myLevelManager;

	@Override
	public void create () {
		setScreen(new Level());
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
			// This checks whether enough time has passed in order to spawn a new customer
//			System.out.println("spawn Next Customer");
			myLevelManager.nextCustomer();
//			 myLevelManager.editMe = true;
		}
		if (myLevelManager.getTimeSinceLastIO() > myLevelManager.getMinIdleTime()){
			// This is statement is checking the time since the last user I/O. If the time has been suffiently long it request the game move to the idle mode
			System.out.println("Activate idle");
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
	}
}
