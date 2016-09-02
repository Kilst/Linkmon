package com.linkmon.model;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.gameobject.items.Item;

public class Shop {
	
	private List<Item> itemsList;
	
	private EventManager eManager;
	
	public Shop(EventManager eManager) {
		this.eManager = eManager;
		
		itemsList = new ArrayList<Item>();
	}

	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return itemsList;
	}
}
