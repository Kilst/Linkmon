package com.linkmon.model;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.gameobject.ObjectFactory;
import com.linkmon.model.gameobject.ObjectId;
import com.linkmon.model.items.components.ItemComponent;

public class Shop {
	
	private List<GameObject> items;
	
	public Shop() {
		items = new ArrayList<GameObject>();
		
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.MEAT));
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.REVIVE_POTION));
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.POOPA_SCOOPA));
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.MEDICINE));
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.HEALTH_POTION));
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.ENERGY_POTION));
	}
	
//	public void addItem(int itemId, int quantity) {
//		GameObject item = ObjectFactory.getInstance().getObjectFromId(itemId);
//		if(items.contains(item))
//			((ItemComponent)item.getExtraComponents()).add(quantity);
//		else
//			items.add(item);
//	}

	public List<GameObject> getItems() {
		// TODO Auto-generated method stub
		return items;
	}

}
