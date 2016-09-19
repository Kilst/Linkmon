package com.linkmon.eventmanager.input;

import com.linkmon.componentmodel.MyVector2;
import com.linkmon.eventmanager.GameEvent;

public class InputEvent extends GameEvent {
	
	private MyVector2 position;
	private int value;

	public InputEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public InputEvent(int eventId, MyVector2 position) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.position = position;
	}

	public MyVector2 getPosition() {
		// TODO Auto-generated method stub
		return position;
	}
	
	public InputEvent(int eventId, int value) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.value = value;
	}

	public int getValue() {
		// TODO Auto-generated method stub
		return value;
	}

}
