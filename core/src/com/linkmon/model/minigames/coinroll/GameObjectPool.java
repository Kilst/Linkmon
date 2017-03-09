package com.linkmon.model.minigames.coinroll;

import com.linkmon.model.gameobject.GameObject;

public class GameObjectPool {
	
	private GameObject object;
	
	private boolean isFreed = true;
	
	public GameObjectPool(GameObject object) {
		this.object = object;
	}
	
	public void reset() {
		((CoinRollItemPhysicsComponent)object.getPhysicsComponent()).reset();
	}

	public boolean isFreed() {
		return isFreed;
	}

	public void setFreed(boolean isFreed) {
		this.isFreed = isFreed;
	}

}
