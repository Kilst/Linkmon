package com.linkmon.model.components;

import com.linkmon.model.gameobject.GameObject;

public interface IUsableComponent {
	
	public void update(GameObject object);
	public void use(GameObject object);

}
