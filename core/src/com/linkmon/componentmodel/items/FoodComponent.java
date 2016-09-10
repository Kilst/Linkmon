package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

public class FoodComponent extends ItemComponent implements UsableItemComponent {
	
	private int feedAmount;
	
	public FoodComponent(int quantity, int price, int type, String itemText, int feedAmount) {
		super(quantity, price, type,itemText);
		this.feedAmount = feedAmount;
	}

	@Override
	public void use(GameObject object, World world) {
		// TODO Auto-generated method stub
		((LinkmonExtraComponents)object.getExtraComponents()).getStatus().addHungerLevel(feedAmount);
	}

	public int getFeedAmount() {
		// TODO Auto-generated method stub
		return feedAmount;
	}
}
