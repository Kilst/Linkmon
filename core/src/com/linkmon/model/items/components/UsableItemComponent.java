package com.linkmon.model.items.components;

import com.linkmon.model.World;
import com.linkmon.model.components.IUsableComponent;
import com.linkmon.model.gameobject.GameObject;

public interface UsableItemComponent {
	
	public void update(GameObject object);
	public void use(GameObject item, GameObject linkmon, World world);

}
