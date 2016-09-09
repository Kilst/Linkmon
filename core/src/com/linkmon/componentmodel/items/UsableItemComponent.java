package com.linkmon.componentmodel.items;

import com.linkmon.componentmodel.World;
import com.linkmon.componentmodel.components.IUsableComponent;
import com.linkmon.componentmodel.gameobject.GameObject;

public interface UsableItemComponent {
	
	public void update(GameObject object);
	public void use(GameObject object, World world);

}
