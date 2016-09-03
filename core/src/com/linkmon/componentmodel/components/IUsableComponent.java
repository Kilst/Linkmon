package com.linkmon.componentmodel.components;

import com.linkmon.componentmodel.gameobject.GameObject;

public interface IUsableComponent {
	
	public void update(GameObject object);
	public void use(GameObject object);

}
