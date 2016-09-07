package com.linkmon.componentmodel.components;

import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;

public interface ICollisionComponent {
	
	public void testCollision(GameObject gameObject, List<GameObject> objects);

	public List<GameObject> getCollisionList();

}
