package com.linkmon.messagesystem;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.eventmanager.messages.MessageEvent;
import com.linkmon.eventmanager.messages.MessageEvents;
import com.linkmon.eventmanager.messages.MessageListener;
import com.linkmon.view.screens.widgets.messages.MessageType;

public class MessageManager implements MessageListener {
	
	// Messages are non-blocking events. As in; they get set and wait to be called, nothing more, nothing to wait for.
	
	private List<MessageEvent> messageQueue;
	private List<MessageEvent> messageQueueRemove;
	private MessageEvent currentMessage;
	
	private List<MessageEvent> networkMessageQueue;
	private List<MessageEvent> networkMessageQueueRemove;
	private MessageEvent currentNetworkMessage;
	
	private EventManager eManager;
	
	public MessageManager(EventManager eManager) {
		this.eManager = eManager;
		messageQueue = new ArrayList<MessageEvent>();
		messageQueueRemove = new ArrayList<MessageEvent>();
		
		networkMessageQueue = new ArrayList<MessageEvent>();
		networkMessageQueueRemove = new ArrayList<MessageEvent>();
		
		this.eManager.addMessageListener(this);
	}
	
	public MessageEvent getCurrentMessage() {
		// TODO Auto-generated method stub
		return currentMessage;
	}
	
	public MessageEvent getCurrentNetworkMessage() {
		// TODO Auto-generated method stub
		return currentNetworkMessage;
	}
	
	private void updateGameMessages() {
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
	
	private void updateNetworkMessages() {
		if(currentNetworkMessage == null) {
			for(MessageEvent message : networkMessageQueue){
				currentNetworkMessage = message; // Set current message
				networkMessageQueueRemove.add(currentNetworkMessage);
				break;
			}
			for(MessageEvent message : networkMessageQueueRemove){
				networkMessageQueue.remove(message); // Remove message
			}
		}
	}

	@Override
	public boolean onNotify(MessageEvent event) {
		// TODO Auto-generated method stub
		switch(event.eventId) {
			case(MessageEvents.CLEAR_CURRENT_MESSAGE) : {
				if(event.messageType == MessageType.GAME_MESSAGE) {
					currentMessage = null;
					updateGameMessages();
				}
				else if(event.messageType == MessageType.NETWORK_MESSAGE) {
					currentNetworkMessage = null;
					updateNetworkMessages();
				}
				break;
			}
			default:{
				if(event.messageType == MessageType.GAME_MESSAGE) {
					messageQueue.add(event);
					updateGameMessages();
				}
				else if(event.messageType == MessageType.NETWORK_MESSAGE) {
					networkMessageQueue.add(event);
					updateNetworkMessages();
				}
				break;
			}
		}
		return false;
	}
}
