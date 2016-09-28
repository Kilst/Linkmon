package com.linkmon.model.gameobject;

import com.badlogic.gdx.Gdx;
import com.linkmon.model.MyVector2;

public class AABB {
	
	private float x;
	private float y;
	
	private float width = 0;
	private float height = 0;
	
	public AABB(float x, float y, float width, float height) {
		this.x = x;
		this.y = y;
		
		this.width = width;
		this.height = height;
	}
	
	public void update(GameObject object) {
		this.x = object.getX();
		this.y = object.getY();
	}
	
	public boolean contains(float x, float y) {
		if(x > this.x && x < this.x + width && y > this.y && y < this.y + height)
			return true;
		else
			return false;
	}

	public boolean contains(MyVector2 position) {
		// TODO Auto-generated method stub
		return contains(position.x, position.y);
	}
	
	public boolean contains(AABB aabb) {
//	    return bottomLeft.x <= aabb.bottomLeft.x && bottomLeft.y <= aabb.bottomLeft.y
//	        && aabb.topRight.x <= topRight.x && aabb.topRight.y <= topRight.y;
	    if (x + width < aabb.x || 
	    		y + height < aabb.y || 
                x > aabb.x + aabb.width || 
               y > aabb.y + aabb.height)
            {
                return false;
            }
	    else {
//	    	Gdx.app.log("AABB", "Collision detected!\nPoop X: " + aabb.x+ "\nScoopa X: " + x
//			+ "\nPoop Width: " + aabb.width + "\nScoopa Width: " + width);
	    	return true;
	    }
	  }

}