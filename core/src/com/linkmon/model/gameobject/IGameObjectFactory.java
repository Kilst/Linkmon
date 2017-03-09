package com.linkmon.model.gameobject;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.battles.LocalBattleLinkmon;

public interface IGameObjectFactory {
	public GameObject createLinkmon(int id);
	public LocalBattleLinkmon createLocalBattleLinkmon(int rank, int targetId);
	public GameObject getObjectFromId(int id);
	public void setEventManager(EventManager eManager);
}
