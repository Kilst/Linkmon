package com.linkmon.model.components;

import java.util.List;

import com.linkmon.model.gameobject.GameObject;

public interface ICollisionComponent {
	
	public void testCollision(GameObject gameObject, List<GameObject> objects);

	public List<GameObject> getCollisionList();

}
