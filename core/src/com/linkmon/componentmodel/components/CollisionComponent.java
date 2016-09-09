package com.linkmon.componentmodel.components;

import java.util.ArrayList;
import java.util.List;

import com.linkmon.componentmodel.gameobject.GameObject;

public class CollisionComponent implements ICollisionComponent {
	
	protected List<GameObject> collideList;
	
	public CollisionComponent() {
		collideList = new ArrayList<GameObject>();
	}
	
	public List<GameObject> getCollisionList() {
		return collideList;
	}

	@Override
	public void testCollision(GameObject gameObject, List<GameObject> objects) {
		// TODO Auto-generated method stub
		
		collideList.clear();
		
		for(GameObject object : objects) {
			if(object != gameObject) {
				if(gameObject.getAabb().contains(object.getAabb())) { // Test for AABB collision
					collideList.add(object); // Add object to list to be processed by derived physics class
				}
			}
		}
	}

}
