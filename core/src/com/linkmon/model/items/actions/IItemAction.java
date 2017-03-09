package com.linkmon.model.items.actions;

import com.linkmon.helpers.CompleteMessage;
import com.linkmon.model.World;
import com.linkmon.model.gameobject.GameObject;

public interface IItemAction {
	
	public CompleteMessage use(GameObject item, GameObject linkmon, World world);

}
