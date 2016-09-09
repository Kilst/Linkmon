package com.linkmon.componentmodel;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.gameobject.ObjectFactory;
import com.linkmon.componentmodel.gameobject.ObjectId;
import com.linkmon.componentmodel.items.ItemComponent;

public class Shop {
	
	private List<GameObject> items;
	
	public Shop() {
		items = new ArrayList<GameObject>();
		
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.MEAT));
		items.add(ObjectFactory.getInstance().getObjectFromId(ObjectId.REVIVE_POTION));
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
