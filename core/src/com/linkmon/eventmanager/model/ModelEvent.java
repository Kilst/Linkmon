package com.linkmon.eventmanager.model;

import com.linkmon.eventmanager.GameEvent;

public class ModelEvent extends GameEvent {
	
	public float x;
	public float y;
	
	public int value;
	public int value2;

	public ModelEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public ModelEvent(int eventId, float x, float y) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public ModelEvent(int eventId, int value) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public ModelEvent(int eventId, int value, int value2) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.value = value;
		this.value2 = value2;
	}
}