package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;

public class AddToWorldItemAction implements IItemAction {

	@Override
	public boolean use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		world.addObjectToWorld(item);
		return true;
	}
}
