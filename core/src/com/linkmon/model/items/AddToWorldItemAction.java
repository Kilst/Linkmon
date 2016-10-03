package com.linkmon.model.items;

import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.view.particles.ParticleIds;

public class AddToWorldItemAction implements IItemAction {

	@Override
	public boolean use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		world.addObjectToWorld(item);
		item.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.GREEN_STAR, item.getX()+(item.getWidth()/2), item.getY()+(item.getHeight()/2)));
		return true;
	}
}
