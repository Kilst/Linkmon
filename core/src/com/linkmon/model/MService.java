package com.linkmon.model;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.gamesave.AESEncryptor;
import com.linkmon.model.gamesave.JsonSaver;
import com.linkmon.view.UIRenderer;

public class MService {
	
	private World world;
	private Player player;
	private Shop shop;
	
	private EventManager eManager;
	
	JsonSaver jsonSaver;
	
	public MService(EventManager eManager) {
		this.eManager = eManager;
		load();
	}

	private void load() {
		
		try {
		
			world = new World(eManager, 1280, 720);
			world.addRenderer(new WorldRenderingComponent(world, "trainingBackground"));
			
			shop = new Shop();
			
			jsonSaver = new JsonSaver(new AESEncryptor());
			
			player = jsonSaver.load();
			player.addeManager(eManager);
			player.setWorld(world);
			
			world.addObjectToWorld(player.getLinkmon());
		}
		catch(Exception e) {
			newGame(0);
			
		}
	}
	
	public void saveGame() {
		jsonSaver = new JsonSaver(new AESEncryptor());
		jsonSaver.save(player);
	}
	
	private void newGame(int eggChoice) {
		
		world = new World(eManager, UIRenderer.WIDTH, UIRenderer.HEIGHT);
		world.addRenderer(new WorldRenderingComponent(world, "trainingBackground"));
		
		player = new Player(eManager, eggChoice, world);
//		jsonSaver = new JsonSaver(new AESEncryptor());
//		jsonSaver.save(player);
		shop = new Shop();
		
		world.addObjectToWorld(player.getLinkmon());
	}

	public World getWorld() {
		return world;
	}

	public Player getPlayer() {
		// TODO Auto-generated method stub
		return player;
	}
	
	public Shop getShop() {
		// TODO Auto-generated method stub
		return shop;
	}
}
