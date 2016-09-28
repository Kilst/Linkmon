package com.linkmon.view.screens.widgets.messages;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.screen.ScreenEvent;
import com.linkmon.eventmanager.screen.ScreenEvents;
import com.linkmon.model.items.ItemIds;


public class ReviveMessageAction implements IMessageAction {
	
	private EventManager eManager;
	
	public ReviveMessageAction(EventManager eManager) {
		this.eManager = eManager;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub
		eManager.notify(new ScreenEvent(ScreenEvents.USE_ITEM, ItemIds.REVIVE_POTION));
	}

}