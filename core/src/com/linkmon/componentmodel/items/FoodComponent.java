package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

public class FoodComponent extends ItemComponent implements UsableItemComponent {
	
	private int feedAmount = 100;

	@Override
	public void use(GameObject object) {
		// TODO Auto-generated method stub
		((LinkmonExtraComponents)object.getExtraComponents()).getStatus().addHungerLevel(feedAmount);
	}

	public int getFeedAmount() {
		// TODO Auto-generated method stub
		return feedAmount;
	}
}
