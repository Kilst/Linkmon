package com.linkmon.eventmanager.screen;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.GameEvent;
import com.linkmon.view.screens.interfaces.MyScreen;

public class ScreenEvent extends GameEvent {
	
	public MyScreen screen;
	public int value;
	public int value2;
	
	public float fValue;
	
	public boolean bool;

	public ScreenEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public ScreenEvent(int eventId, MyScreen screen) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.screen = screen;
	}
	
	public ScreenEvent(int eventId, int value) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.value = value;
	}

	public ScreenEvent(int eventId, int value, MyScreen screen) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.screen = screen;
		this.value = value;		
	}

	public ScreenEvent(int eventId, int value, int value2) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.value = value;
		this.value2 = value2;
	}

	public ScreenEvent(int eventId, boolean bool) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.bool = bool;
	}

	public ScreenEvent(int eventId, float fValue) {
		super(eventId);
		// TODO Auto-generated constructor stub

		this.fValue = fValue;
	}
}
