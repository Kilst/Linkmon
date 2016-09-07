package com.linkmon.componentmodel;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectId;
import com.linkmon.componentmodel.gamesave.AESEncryptor;
import com.linkmon.componentmodel.gamesave.JsonSaver;
import com.linkmon.eventmanager.EventManager;

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
		
//			world = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
//			
//			shop = new Shop();
//			
//			jsonSaver = new JsonSaver(new AESEncryptor());
//			
//			player = jsonSaver.load();
//			player.addeManager(eManager);
			
			world.addObjectToWorld(player.getLinkmon());
		}
		catch(Exception e) {
			newGame(0);
			
			GameObject poopaScoopa = ObjectFactory.getInstance().getObjectFromId(ObjectId.POOPA_SCOOPA);
			world.addObjectToWorld(poopaScoopa);
		}
	}
	
	private void loadGame() {
		
	}
	
	private void newGame(int eggChoice) {
		
		world = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		player = new Player(eManager, eggChoice);
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
