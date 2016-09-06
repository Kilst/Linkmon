package com.linkmon.componentmodel.gamesave;

import com.linkmon.componentmodel.Player;

public interface ISaveManager {
	
	public void save(Player player);
	public Player load();
	
}
