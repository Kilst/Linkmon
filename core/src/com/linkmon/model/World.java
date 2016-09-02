package com.linkmon.model;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.view.ViewEvent;
import com.linkmon.eventmanager.view.ViewEvents;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.model.gameobject.linkmon.Linkmon;

public class World {
	
	private boolean isLightOn = true;
	
	private final float width;
	private final float height;
	
	public final float scaleX;
	public final float scaleY;
	
	public OnlineBattle onlineBattle;
	
	private EventManager eManager;
	
	private Linkmon linkmon;
	
	public World(float screenWidth, float screenHeight, EventManager eManager) {
		this.eManager = eManager;
		
		this.width = screenWidth;
		this.height = screenHeight;
		
		scaleX = width/640;
		scaleY = height/480;
	}
	
	public World(float screenWidth, float screenHeight, boolean loadSave) {
		this.width = screenWidth;
		this.height = screenHeight;
		
		scaleX = width/640;
		scaleY = height/480;
		
		// Get player and linkmon from json
	}
	
	public void update() {
		linkmon.update();
	}
	
//	public OnlineBattle createOnlineBattle(BattleLinkmon player, BattleLinkmon opponent) {
//		onlineBattle = new OnlineBattle(player, opponent, this.eManager);
//		return onlineBattle;
//	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
	}

	public boolean isLightOn() {
		return isLightOn;
	}

	public void setLight(boolean swap) {
		isLightOn = swap;
		eManager.notify(new ViewEvent(ViewEvents.UPDATE_LIGHT, isLightOn));
	}

	public Linkmon getLinkmon() {
		return linkmon;
	}

	public void addLinkmonToWorld(Linkmon linkmon) {
		this.linkmon = linkmon;
		this.linkmon.addedToWorld(this);
	}
}
