package com.linkmon.componentmodel.gamesave;

import com.linkmon.componentmodel.World;

public interface ISaveManager {
	
	public void save(World world);
	public World load();
	
}
