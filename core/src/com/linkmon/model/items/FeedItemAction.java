package com.linkmon.model.items;

import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class FeedItemAction implements IItemAction {
	
	private int feedAmount;
	
	public FeedItemAction(int feedAmount) {
		this.feedAmount = feedAmount;
	}

	@Override
	public boolean use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().getHungerLevel() < 90) {
			((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().addHungerLevel(feedAmount);
			return true;
		}
		else
			return false;
	}

}
