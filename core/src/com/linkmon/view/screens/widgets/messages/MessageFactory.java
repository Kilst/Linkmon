package com.linkmon.view.screens.widgets.messages;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;

public class MessageFactory {
	
	private EventManager eManager;
	
	public MessageFactory(EventManager eManager) {
		this.eManager = eManager;
	}
	
	public MessageBox getMessage(MessageEvent event) {
		
		MessageBox messageBox = null;
		
		switch(event.eventId) {
			case(MessageEvents.GENERIC_MESSAGE) : {
				messageBox = new MessageBox(event.messageType, event.heading, event.message, eManager);
				break;
			}
			case(MessageEvents.DISCONNECTED_SERVER) : {
				messageBox = new MessageBox(event.messageType, event.heading, event.message, eManager);
				messageBox.addMessageAction(new ReturnToMainMessageAction(eManager));
				break;
			}
			case(MessageEvents.BATTLE_ENDED) : {
				messageBox = new MessageBox(event.messageType, event.heading, event.message, eManager);
				messageBox.addMessageAction(new ReturnToServerMessageAction(eManager));
				break;
			}
		}
		
		return messageBox;
	}

}
