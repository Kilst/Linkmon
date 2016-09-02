package com.linkmon.eventmanager.model;

import com.linkmon.eventmanager.GameEvent;

public class ModelEvent extends GameEvent {
	
	public int x;
	public int y;

	public ModelEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public ModelEvent(int eventId, int x, int y) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}
}