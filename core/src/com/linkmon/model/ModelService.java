package com.linkmon.model;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;

public class ModelService {
	
	private Player player;
	
	public OnlineBattle onlineBattle;
	
	private EventManager eManager;
	
	private World world;
	
	public ModelService(float screenWidth, float screenHeight, EventManager eManager) {
		this.eManager = eManager;
		
		world = new World(screenWidth, screenHeight, eManager);
		
		player = new Player(this.eManager, world);

	}
	
	public ModelService(float screenWidth, float screenHeight, EventManager eManager,  boolean loadSave) {		
		// Get player and linkmon from json
	}
	
	public ModelService(String playerName, int eggChoice, float screenWidth, float screenHeight, EventManager eManager) {
		// TODO Auto-generated constructor stub
		this.eManager = eManager;
		
		world = new World(screenWidth, screenHeight, eManager);
		
		player = new Player(playerName, eggChoice, this.eManager, world);
	}

	public ModelService(Player player, float screenWidth, float screenHeight, EventManager eManager) {
		// TODO Auto-generated constructor stub
		this.eManager = eManager;
		
		world = new World(screenWidth, screenHeight, eManager);
		
		this.player = player;
		this.player.seteManager(eManager);
		player.getLinkmon().seteManager(eManager);
		player.getLinkmon().addedToWorld(world);
	}

	public void update() {
		player.getLinkmon().update();
	}
	
	public OnlineBattle createOnlineBattle(BattleLinkmon player, BattleLinkmon opponent) {
		onlineBattle = new OnlineBattle(player, opponent, this.eManager);
		return onlineBattle;
	}

	public Player getPlayer() {
		return player;
	}

	public World getWorld() {
		// TODO Auto-generated method stub
		return world;
	}
}
