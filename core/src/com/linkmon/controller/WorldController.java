package com.linkmon.controller;

import java.util.Random;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.helpers.Timer;
import com.linkmon.model.World;
import com.linkmon.model.WorldRenderingComponent;
import com.linkmon.model.battles.BattleWorld;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.view.screens.DebuggingScreen;

public class WorldController implements ScreenListener {
	
	private World gameWorld;
	
	private World world;
	
	private Timer objectSpawner;
	
	private EventManager eManager;
	
	public WorldController(World world, EventManager eManager) {
		this.eManager = eManager;
		gameWorld = world;
		gameWorld.setActive(true);
		this.world = world;
	}
	
	public void update() {
		
		gameWorld.update();
		
		if(world.isUpdating() && world != gameWorld)
			world.update();
		
		if(objectSpawner != null &&objectSpawner.checkTimer()) {
			GameObject object = ObjectFactory.getInstance().getObjectFromId(ObjectId.POOPA_SCOOPA);
			Random rnd = new Random();
			object.setY(50+rnd.nextInt((int)(gameWorld.getHeight()-100)));
			gameWorld.addObjectToWorld(object);
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
	
	private void setBattleWorld() {
		world = new BattleWorld(eManager, 1280, 720);
		world.addRenderer(new WorldRenderingComponent(world, "battleBackground"));
		((WorldRenderingComponent)world.getRenderer()).setBackground();
		gameWorld.setActive(false);
	}
	
	private void setGameWorld() {
		world = gameWorld;
		gameWorld.setActive(true);
	}
	
	private void setMiniGameWorld() {
		world = gameWorld;
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
			case(ScreenEvents.START_LOCAL_BATTLE): {
				setBattleWorld();
				return false;
			}
			case(ScreenEvents.RETURN_TO_MAIN_GAME): {
				setGameWorld();
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

	public World getGameWorld() {
		// TODO Auto-generated method stub
		return gameWorld;
	}

	public void setWorld(World world) {
		// TODO Auto-generated method stub
		this.world = world;
		gameWorld.setActive(false);
	}

}
