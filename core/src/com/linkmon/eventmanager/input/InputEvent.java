package com.linkmon.eventmanager.input;

import com.linkmon.componentmodel.MyVector2;
import com.linkmon.eventmanager.GameEvent;

public class InputEvent extends GameEvent {
	
	private MyVector2 position;

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

}
