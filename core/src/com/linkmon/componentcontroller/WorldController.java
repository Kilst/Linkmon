package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.World;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.eventmanager.screen.ScreenListener;
import com.linkmon.view.screens.DebuggingScreen;

public class WorldController implements ScreenListener {
	
	private World world;
	
	public WorldController(World world) {
		this.world = world;
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

	@Override
	public boolean onNotify(ScreenEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(ScreenEvents.DEBUGGING): {
				((DebuggingScreen)event.screen).updateObjectCount(world.getObjects().size());
				return false;
			}
		}
		return false;
	}

}
