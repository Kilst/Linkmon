package com.linkmon.model.items;

import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;

public class AddToWorldItemAction implements IItemAction {

	@Override
	public boolean use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		world.addObjectToWorld(item);
		return true;
	}
}
