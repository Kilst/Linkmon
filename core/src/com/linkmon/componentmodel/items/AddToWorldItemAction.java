package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;

public class AddToWorldItemAction implements IItemAction {

	@Override
	public void use(GameObject item, GameObject linkmon, World world) {
		// TODO Auto-generated method stub
		world.addObjectToWorld(item);
	}
}
