package com.linkmon.model.items;

import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;

public interface IItemAction {
	
	public boolean use(GameObject item, GameObject linkmon, World world);

}
