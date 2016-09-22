package com.linkmon.model.gameobject;

import com.linkmon.eventmanager.EventManager;

public interface IGameObjectFactory {
	public GameObject createLinkmon(int id);
	public GameObject getObjectFromId(int id);
	public void setEventManager(EventManager eManager);
}
