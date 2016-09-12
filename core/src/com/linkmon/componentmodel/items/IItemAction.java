package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.gameobject.GameObject;

public interface IItemAction {
	
	public void use(GameObject item, GameObject linkmon, World world);

}
