package com.linkmon.model.items.actions;

import com.linkmon.helpers.CompleteMessage;
import com.linkmon.helpers.CompleteMessageFactory;
import com.linkmon.helpers.CompleteMessageId;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class ReviveItemAction implements IItemAction {

	@Override
	public CompleteMessage use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().isDead()) {
			((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setDead(false);
			return new CompleteMessage(true, null, null);
		}
		else
			return CompleteMessageFactory.getMessageFromId(CompleteMessageId.NOT_DEAD_EROR);
	}

}
