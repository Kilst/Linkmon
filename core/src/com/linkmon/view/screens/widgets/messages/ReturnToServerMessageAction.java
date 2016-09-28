package com.linkmon.view.screens.widgets.messages;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.view.screens.ScreenType;

public class ReturnToServerMessageAction implements IMessageAction {
	
	private EventManager eManager;
	
	public ReturnToServerMessageAction(EventManager eManager) {
		this.eManager = eManager;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.ONLINE_SCREEN));
	}
}
