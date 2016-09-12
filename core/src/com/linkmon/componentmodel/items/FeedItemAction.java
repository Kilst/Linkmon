package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

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
