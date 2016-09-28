package com.linkmon.view.screens.widgets.messages;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.view.screens.ScreenType;

public class ReturnToMainMessageAction implements IMessageAction {
	
	private EventManager eManager;
	
	public ReturnToMainMessageAction(EventManager eManager) {
		this.eManager = eManager;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.SWAP_SCREEN, ScreenType.MAIN_UI));
	}
}
