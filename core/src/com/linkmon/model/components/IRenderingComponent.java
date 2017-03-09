package com.linkmon.model.components;

import com.linkmon.model.gameobject.GameObject;

public interface IRenderingComponent {
	public void update(GameObject object);

	public void dispose();
}
