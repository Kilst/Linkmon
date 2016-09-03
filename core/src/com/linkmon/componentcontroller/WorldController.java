package com.linkmon.componentcontroller;

import com.linkmon.componentmodel.World;

public class WorldController {
	
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

}
