package com.linkmon.model.items;

import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.view.particles.ParticleIds;

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
			linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.MEAT, linkmon.getX()+(linkmon.getWidth()/2), linkmon.getY()+(linkmon.getHeight()/2)));
			return true;
		}
		else
			return false;
	}

}
