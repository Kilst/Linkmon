package com.linkmon.componentcontroller;

import com.badlogic.gdx.Gdx;
import com.linkmon.componentmodel.Player;
import com.linkmon.componentmodel.World;
import com.linkmon.eventmanager.EventManager;

public class MiniGameController {
	
	private Player player;
	
	private World gameWorld;
	
	public MiniGameController(EventManager eManager, Player player) {
		gameWorld = new World(eManager, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		gameWorld.addObjectToWorld(player.getLinkmon());
	}

}
