package com.linkmon.model.gameobject;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.battles.LocalBattleLinkmon;

public class SomeOtherObjectFactory implements IGameObjectFactory {

	@Override
	public GameObject createLinkmon(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameObject getObjectFromId(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setEventManager(EventManager eManager) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LocalBattleLinkmon createLocalBattleLinkmon(int rank, int targetId) {
		// TODO Auto-generated method stub
		return null;
	}

}
