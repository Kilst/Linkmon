package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;
import com.linkmon.componentmodel.linkmon.LinkmonExtraComponents;

public class ReviveItemAction implements IItemAction {

	@Override
	public void use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		((LinkmonExtraComponents)linkmon.getExtraComponents()).getStatus().setDead(false);
	}

}
