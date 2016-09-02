package com.linkmon.messagesystem;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.messages.MessageListener;

public class MessageManager implements MessageListener {
	
	// Messages are non-blocking events. As in; they get set and wait to be called, nothing more, nothing to wait for.
	
	private List<MessageEvent> messageQueue;
	private List<MessageEvent> messageQueueRemove;
	private MessageEvent currentMessage;
	private EventManager eManager;
	
	public MessageManager(EventManager eManager) {
		this.eManager = eManager;
		messageQueue = new ArrayList<MessageEvent>();
		messageQueueRemove = new ArrayList<MessageEvent>();
		this.eManager.addMessageListener(this);
	}
	
	public MessageEvent getCurrentMessage() {
		// TODO Auto-generated method stub
		return currentMessage;
	}
	
	private void update() {
		if(currentMessage == null) {
			for(MessageEvent message : messageQueue){
				currentMessage = message; // Set current message
				messageQueueRemove.add(currentMessage);
				break;
			}
			for(MessageEvent message : messageQueueRemove){
				messageQueue.remove(message); // Remove message
			}
		}
	}

	@Override
	public boolean onNotify(MessageEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(MessageEvents.CLEAR_CURRENT_MESSAGE) : {
				currentMessage = null;
				update();
				break;
			}
			default:{
				messageQueue.add(event);
				update();
				break;
			}
		}
		return false;
	}
}
