package com.linkmon.componentcontroller;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.helpers.Timer;

public class MiniGameController {
	
	private Player player;
	
	private World gameWorld;
	
	private Timer spawnTimer;
	
	public MiniGameController(EventManager eManager) {
		gameWorld = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameWorld.addObjectToWorld(ObjectFactory.getInstance().createLinkmon(1));
		
		spawnTimer = new Timer(1, true);
		spawnTimer.start();
	}
	
	public World getWorld() {
		return gameWorld;
	}
	
	public void update() {
		gameWorld.update();
		if(spawnTimer.checkTimer())
			eggSpawner();
	}
	
	private void eggSpawner() {
		GameObject object = ObjectFactory.getInstance().getObjectFromId(77);
		Random rnd = new Random();
		object.setX(rnd.nextInt((int)(gameWorld.getWidth())));
		object.setY(Gdx.graphics.getHeight());
		gameWorld.addObjectToWorld(object);
	}

}
