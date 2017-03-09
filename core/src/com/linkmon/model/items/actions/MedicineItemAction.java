package com.linkmon.model.items.actions;

import com.linkmon.eventmanager.model.ModelEvent;
import com.linkmon.eventmanager.model.ModelEvents;
import com.linkmon.helpers.CompleteMessage;
import com.linkmon.helpers.CompleteMessageFactory;
import com.linkmon.helpers.CompleteMessageId;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;
import com.linkmon.view.particles.ParticleIds;

public class MedicineItemAction implements IItemAction {

	@Override
	public CompleteMessage use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().isSick()) {
			((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setSick(false); // Cure linkmon
			linkmon.getWorld().geteManager().notify(new ModelEvent(ModelEvents.ADD_PARTICLE_EFFECT, ParticleIds.STAR, linkmon.getX()+(linkmon.getWidth()/2), linkmon.getY()+(linkmon.getHeight()/2)));
			return new CompleteMessage(true, null, null); // True = use item
		}
		return CompleteMessageFactory.getMessageFromId(CompleteMessageId.NOT_SICK_ERROR); // False = don't use item
	}

}
