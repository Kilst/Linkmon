package com.linkmon.componentmodel;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;

public class MService {
	
	private World world;
	private Player player;
	private Shop shop;
	
	private EventManager eManager;
	
	public MService(EventManager eManager) {
		this.eManager = eManager;
		load();
	}

	private void load() {
		newGame(0);
	}
	
	private void loadGame() {
		
	}
	
	private void newGame(int eggChoice) {
		
		world = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		player = new Player(eManager, eggChoice);
		
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
}
