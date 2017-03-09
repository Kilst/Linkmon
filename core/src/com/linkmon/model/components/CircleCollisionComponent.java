package com.linkmon.model.components;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.gameobject.GameObject;

public class CircleCollisionComponent implements ICollisionComponent {
	
	
protected List<GameObject> collideList;

	
	public CircleCollisionComponent() {
		collideList = new ArrayList<GameObject>();
	}
	
	public List<GameObject> getCollisionList() {
		return collideList;
	}
	
	public boolean circlesColliding(int x1,int y1,int radius1,int x2,int y2,int radius2)
	{
	    //compare the distance to combined radii
	    int dx = x2 - x1;
	    int dy = y2 - y1;
	    int radii = radius1 + radius2;
	    if ( ( dx * dx )  + ( dy * dy ) < radii * radii ) 
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}

	@Override
	public void testCollision(GameObject gameObject, List<GameObject> objects) {
		// TODO Auto-generated method stub
		
		collideList.clear();
		
		for(GameObject object : objects) {
			if(object != gameObject) {
				if(circlesColliding((int)(gameObject.getX()+gameObject.getHalfWidth()), (int)(gameObject.getY()+gameObject.getHalfHeight()), (int)(gameObject.getHalfWidth()), (int)(object.getX()+object.getHalfWidth()), (int)(object.getY()+object.getHalfHeight()), (int)(object.getHalfWidth()))) { // Test for circle collision
					if(!collideList.contains(object)) {
						collideList.add(object); // Add object to list to be processed by derived physics class
					}
				}
			}
		}
	}

}
