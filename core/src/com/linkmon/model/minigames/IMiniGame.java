package com.linkmon.model.minigames;

import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;

public interface IMiniGame {
	
	public World getWorld();
	public void update();
	public GameObject getPlayer();
	public void movePlayer(float x, float y);
	public void restart();

}
