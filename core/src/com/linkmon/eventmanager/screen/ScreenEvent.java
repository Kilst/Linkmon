package com.linkmon.eventmanager.screen;

import com.linkmon.eventmanager.GameEvent;
import com.linkmon.view.screens.interfaces.MyScreen;

public class ScreenEvent extends GameEvent {
	
	public MyScreen screen;
	public int screenType;

	public ScreenEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public ScreenEvent(int eventId, MyScreen screen) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.screen = screen;
	}
	
	public ScreenEvent(int eventId, int screenType) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.screenType = screenType;
	}
}
