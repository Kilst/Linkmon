package com.linkmon.controller;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.controller.ControllerEvent;
import com.linkmon.eventmanager.controller.ControllerEvents;
import com.linkmon.eventmanager.controller.ControllerListener;
import com.linkmon.model.World;

public class WorldController implements ControllerListener {
	
	
	private World world;
	private EventManager eManager;
	
	public WorldController(EventManager eManager, World world) {
		this.eManager = eManager;
		this.eManager.addControllerListener(this);
		this.world = world;
	}
	
	private void swapLight() {
		if(world.isLightOn())
			world.setLight(false);
		else
			world.setLight(true);
	}

	@Override
	public boolean onNotify(ControllerEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ControllerEvents.LIGHT_SWAP): {
				swapLight();
				break;
			}
		}
		return false;
	}

}
