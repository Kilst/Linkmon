package com.linkmon.eventmanager.messages;

import com.linkmon.eventmanager.GameEvent;

public class MessageEvent extends GameEvent {
	
	public int messageType;
	
	public String message;
	
	public boolean returnToMain;
	
	public int rank;
	
	public String[] messages;
	
	public int value;
	
	public MessageEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public MessageEvent(int eventId, String message) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.message = message;
	}
	
	public MessageEvent(int eventId, String message, boolean returnToMain) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.message = message;
		this.returnToMain = returnToMain;
	}
	
	public MessageEvent(int eventId, String message, int rank) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.message = message;
		this.rank = rank;
	}

	public MessageEvent(int eventId, int messageType, int value, String[] messages) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.messageType = messageType;
		this.value = value;
		this.messages = messages;
	}

	public MessageEvent(int eventId, int messageType, String message) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.messageType = messageType;
		this.message = message;
	}

	public MessageEvent(int eventId, int messageType) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.messageType = messageType;
	}
}
