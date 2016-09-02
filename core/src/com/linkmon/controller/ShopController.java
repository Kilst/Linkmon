package com.linkmon.controller;

import java.util.List;

import com.linkmon.eventmanager.EventManager;
import com.linkmon.model.Shop;
import com.linkmon.model.gameobject.items.Item;
import com.linkmon.model.gameobject.items.ItemFactory;
import com.linkmon.model.gameobject.items.ItemIds;

public class ShopController {

	private Shop shop;
	
	private EventManager eManager;
	
	public ShopController(EventManager eManager) {
		shop = new Shop(eManager);
		this.eManager = eManager;
		
		addItem(ItemFactory.getItemFromId(ItemIds.REVIVE_POTION));
		addItem(ItemFactory.getItemFromId(ItemIds.LARGE_MEAT));
		addItem(ItemFactory.getItemFromId(ItemIds.SUPER_MEAT));
	}
	public List<Item> getItems() {
		// TODO Auto-generated method stub
		return shop.getItems();
	}
	
	private void addItem(Item newItem) {
		boolean match = false;
		for(Item item : shop.getItems()) {
			if(item.getId() == newItem.getId()) {
				item.add(newItem.getQuantity());
				match = true;
				break;
			}
		}
		if(!match)
			shop.getItems().add(newItem);
	}
	
	private void removeItem(Item item) {
		item.remove();
		if(item.getQuantity() < 1)
			shop.getItems().remove(item);
	}
}
