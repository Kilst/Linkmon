package com.linkmon.model.gamesave;

import com.linkmon.model.Player;

public interface ISaveManager {
	
	public void save(Player player);
	public Player load();
	
}
