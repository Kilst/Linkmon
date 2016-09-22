package com.linkmon.model.items;

import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;
import com.linkmon.model.linkmon.LinkmonExtraComponents;

public class ReviveItemAction implements IItemAction {

	@Override
	public boolean use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		if(((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().isDead()) {
			((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setDead(false);
			return true;
		}
		else
			return false;
	}

}
