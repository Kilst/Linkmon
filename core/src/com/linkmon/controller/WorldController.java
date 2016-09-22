package com.linkmon.controller;

import java.util.Random;

import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.Timer;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.view.screens.DebuggingScreen;

public class WorldController implements ScreenListener {
	
	private World world;
	
	private Timer objectSpawner;
	
	public WorldController(World world) {
		this.world = world;
	}
	
	public void update() {
		world.update();
		
		if(objectSpawner != null &&objectSpawner.checkTimer()) {
			GameObject object = ObjectFactory.getInstance().getObjectFromId(ObjectId.POOPA_SCOOPA);
			Random rnd = new Random();
			object.setY(50+rnd.nextInt((int)(world.getHeight()-100)));
			world.addObjectToWorld(object);
		}
	}
	
	public World getWorld() {
		return world;
	}
	
	public void swapLight() {
		if(world.isLightOn())
			world.setLightOn(false);
		else
			world.setLightOn(true);
	}
	
	private void startObjectSpawner() {
		
		objectSpawner = new Timer(1, true);
		objectSpawner.start();
	}
	
	private void stopObjectSpawner() {
		
		objectSpawner.stop();
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.LIGHT_SWAP): {
				swapLight();
				return false;
			}
			case(ScreenEvents.DEBUGGING): {
				((DebuggingScreen)event.screen).updateObjectCount(world.getObjects().size());
				return false;
			}
			case(ScreenEvents.DEBUGGING_SPAWNER): {
				startObjectSpawner();
//				if(objectSpawner != null) {
//					stopObjectSpawner();
//					objectSpawner = null;
//				}
				return false;
			}
		}
		return false;
	}

}
