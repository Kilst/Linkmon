package com.linkmon.componentmodel.components;

import com.linkmon.componentmodel.gameobject.GameObject;

public interface ITimerComponent {
	public void update(GameObject object);
	
	public boolean isRunning();
	
	public void startAll();
	public void stopAll();
}
