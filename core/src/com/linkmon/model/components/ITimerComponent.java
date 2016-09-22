package com.linkmon.model.components;

import com.linkmon.model.gameobject.GameObject;

public interface ITimerComponent {
	public void update(GameObject object);
	
	public boolean isRunning();
	
	public void startAll();
	public void stopAll();
}
