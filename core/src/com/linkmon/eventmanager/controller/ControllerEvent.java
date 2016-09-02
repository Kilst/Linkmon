package com.linkmon.eventmanager.controller;

import com.linkmon.eventmanager.GameEvent;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.linkmon.BattleLinkmon;
import com.linkmon.model.gameobject.linkmon.Move;
import com.linkmon.model.gameobject.poop.Poop;

public class ControllerEvent extends GameEvent {
	public String serverWelcome;
	
	public BattleLinkmon linkmon;
	
	public int myHealth;
	public int oppHealth;

	public Move move;
	
	public boolean status;

	public int screen;
	
	public Item item;

	public Poop poop;
	
	public int[] values;
	
	public ControllerEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	
	public ControllerEvent(int eventId, boolean status) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.status = status;
	}

	public ControllerEvent(int eventId, int screen) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.screen = screen;
	}

	public ControllerEvent(int eventId, String serverWelcome) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.serverWelcome = serverWelcome;
	}

	public ControllerEvent(int eventId, BattleLinkmon linkmon) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.linkmon = linkmon;
	}

	public ControllerEvent(int eventId, int myHealth, int oppHealth) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.myHealth = myHealth;
		this.oppHealth = oppHealth;
	}
	
	public ControllerEvent(int eventId, Item item) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.item = item;
	}

	public ControllerEvent(int eventId, Poop poop) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.poop = poop;
	}

	public ControllerEvent(int eventId, Move move) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.move = move;
	}

	public ControllerEvent(int eventId, int[] values) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.values = values;
	}
}
