package com.linkmon.eventmanager.screen;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.eventmanager.GameEvent;
import com.linkmon.view.screens.interfaces.MyScreen;

public class ScreenEvent extends GameEvent {
	
	public MyScreen screen;
	public int value;
	public GameObject gameObject;

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
	
	public ScreenEvent(int eventId, GameObject object) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.gameObject = object;
	}

	public ScreenEvent(int eventId, int value, MyScreen screen) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.screen = screen;
		this.value = value;		
	}
}
