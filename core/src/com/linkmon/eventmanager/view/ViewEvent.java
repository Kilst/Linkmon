package com.linkmon.eventmanager.view;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.GameEvent;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.poop.Poop;

public class ViewEvent extends GameEvent {
	
	public float x;
	public float y;
	
	public boolean status;
	public List<Object> objects;
	public List<Poop> poopList;
	public Poop poop;
	
	public Item item;
	public int value;
	public String str;
	public int value2;
	
	public ViewEvent(int eventId) {
		super(eventId);
		// TODO Auto-generated constructor stub
	}
	public ViewEvent(int eventId, String str) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.str = str;
	}
	public ViewEvent(int eventId, float x, float y) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
	}

	public ViewEvent(int eventId, boolean status) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.status = status;
	}
	
	public ViewEvent(int eventId, ArrayList<Object> objects) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.objects = objects;
	}
	public ViewEvent(int eventId, List<Poop> poopList) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.poopList = poopList;
	}
	public ViewEvent(int eventId, Item item) {
		super(eventId);
		// TODO Auto-generated constructor stub
		
		this.item = item;
	}
	public ViewEvent(int eventId, Poop poop) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.poop = poop;
	}
	
	public ViewEvent(int eventId, int value) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.value = value;
	}
	
	public ViewEvent(int eventId, int value, int value2) {
		super(eventId);
		// TODO Auto-generated constructor stub
		this.value = value;
		this.value2 = value2;
	}
}