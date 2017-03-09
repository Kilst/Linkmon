package com.linkmon.controller;

import com.badlogic.gdx.Gdx;
import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.model.World;
import com.linkmon.model.minigames.IMiniGame;
import com.linkmon.model.minigames.MiniGameIds;
import com.linkmon.model.minigames.coinroll.CoinRoll;
import com.linkmon.model.minigames.duckracing.DuckRacing;
import com.linkmon.view.screens.ScreenType;

public class MiniGameController implements ScreenListener {
	
	EventManager eManager;
	IMiniGame miniGame;
	
	World world;
	
	WorldController worldController;
	
	public MiniGameController(EventManager eManager, WorldController worldController) {
		this.eManager = eManager;
		this.worldController = worldController;
		eManager.addScreenListener(this);
	}
	
	public void setMiniGame(int type) {
		switch(type) {
			case(MiniGameIds.DUCK_RACING) : {
				miniGame = new DuckRacing(eManager);
				worldController.setWorld(miniGame.getWorld());
				miniGame.getWorld().setUpdating(true);
				break;
			}
			case(MiniGameIds.COIN_ROLL) : {
				miniGame = new CoinRoll(eManager);
				worldController.setWorld(miniGame.getWorld());
				miniGame.getWorld().setUpdating(true);
				break;
			}
		}
	}
	
	public World getWorld() {
		return miniGame.getWorld();
	}
	
	public void update() {
		if(miniGame != null)
			miniGame.update();
	}

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
		
			case(ScreenEvents.OPEN_MINIGAME): {
				setMiniGame(event.value);
				return false;
			}
		
			case(ScreenEvents.MOVE_PLAYER) : {
				miniGame.movePlayer(event.value, 0);
				
				Gdx.app.log("MiniGameController", "Moving player");
				return false;
			}
			
			case(ScreenEvents.RESTART_MINI_GAME) : {
				miniGame.restart();
				
				Gdx.app.log("MiniGameController", "Restart");
				return false;
			}
		}
		return false;
	}

}
